package com.gazim.gmessenger.presentation.features.login

import com.gazim.gmessenger.domain.model.AuthenticationForm
import com.gazim.gmessenger.domain.model.GMessengerServer
import com.gazim.gmessenger.domain.usecase.GetAvailableServersUseCase
import com.gazim.gmessenger.domain.usecase.GetSessionUseCaseImpl
import com.gazim.gmessenger.domain.usecase.OnLogInUseCase
import com.gazim.gmessenger.domain.usecase.PingUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.login.LoginAction.*
import com.gazim.gmessenger.presentation.features.login.LoginSideEffect.*
import com.gazim.gmessenger.presentation.model.toUI
import kotlinx.coroutines.*
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import kotlin.time.DurationUnit

private typealias IntentScope = SimpleSyntax<LoginState, LoginSideEffect>

// todo: Take out actions
class LoginViewModel(
    private val onLogInUseCase: OnLogInUseCase,
    private val getSessionUseCase: GetSessionUseCaseImpl,
    private val getAvailableServersUseCase: GetAvailableServersUseCase,
    private val pingUseCase: PingUseCase
) : BaseViewModel<LoginState, LoginSideEffect, LoginAction>() {
    //    private val notificationService: INotificationService by inject(INotificationService::class.java)
    override val container: Container<LoginState, LoginSideEffect> = container(initialState = LoginState())

    private var loggingJob: Job = Job()
    private var pingJob: Job? = null
    private var availableServers: List<GMessengerServer> = emptyList()

    override fun handleAction(action: LoginAction) {
        intent {
            when (action) {
                is OnChangeLogin -> reduce { state.copy(login = action.login) }
                is OnLogInClick -> login(state.login.text, state.password.text)
                is OnRegisterClick -> {
                    loggingJob.cancelAndJoin()
                    postSideEffect(ToRegisterScreen)
                }

                is OnPasswordVisibilityClick -> reduce { state.copy(passwordVisibility = !state.passwordVisibility) }
                is OnChangePassword ->
                    reduce {
                        state.copy(
                            password = action.password,
                            showPasswordVisibilityButton = action.password.text.isNotEmpty(),
                        )
                    }

                is CancelLoggingIn -> loggingJob.cancel()
                is CloseDialog -> closeDialog()
                is OpenDialog -> openDialog()
            }
        }
    }

    private suspend fun IntentScope.closeDialog() {
        reduce { state.copy(dialogIsOpened = false) }
        pingJob?.cancelAndJoin()
    }

    private suspend fun IntentScope.openDialog() {
        reduce { state.copy(dialogIsOpened = true) }
        pingJob = viewModelScope.launch {
            availableServers = getAvailableServersUseCase()
            println(availableServers)
            reduce { state.copy(servers = availableServers.map { it.toUI(0) }) }
            availableServers.forEachIndexed { index, server ->
                launch {
                    while (true) {
                        runCatching {
                            pingUseCase(server.url).toLong(DurationUnit.MILLISECONDS)
                        }.onSuccess { ping ->
                            reduce {
                                state.copy(servers = availableServers.mapIndexed { i, s ->
                                    s.toUI(
                                        if (i == index) ping
                                        else 0
                                    )
                                })
                            }
                        }
//                            .onFailure(Throwable::printStackTrace)
                        delay(5_000)
                    }
                }
            }
        }
    }

    private suspend fun IntentScope.login(
        login: String,
        password: String,
    ) {
        reduce { state.copy(loggingInProgress = true) }
        coroutineScope {
            loggingJob =
                launch {
                    runCatching {
                        onLogInUseCase(AuthenticationForm(login, password))
                    }.onFailure {
                        if (it is CancellationException) return@onFailure
                        postSideEffect(UnableConnectToServer)
                        it.printStackTrace()
                    }.onSuccess {
                        if (!it) return@onSuccess postSideEffect(WrongLoginOrPassword)
                        val session = getSessionUseCase()
                        if (session != null) return@onSuccess postSideEffect(ToChatsScreen(session))
                        destroyViewModel()
                    }
                }
        }
        reduce { state.copy(loggingInProgress = false) }
    }
}
