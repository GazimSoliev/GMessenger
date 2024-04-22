package com.gazim.gmessenger.presentation.features.login

import com.gazim.gmessenger.domain.model.LoginPasswordModel
import com.gazim.gmessenger.domain.usecase.GetSessionUseCaseImpl
import com.gazim.gmessenger.domain.usecase.OnLogInUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.login.LoginAction.*
import com.gazim.gmessenger.presentation.features.login.LoginSideEffect.*
import kotlinx.coroutines.*
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

private typealias IntentScope = SimpleSyntax<LoginState, LoginSideEffect>

// todo: Take out actions
class LoginViewModel(
    private val onLogInUseCase: OnLogInUseCase,
    private val getSessionUseCase: GetSessionUseCaseImpl,
) : BaseViewModel<LoginState, LoginSideEffect, LoginAction>() {
    //    private val notificationService: INotificationService by inject(INotificationService::class.java)
    override val container: Container<LoginState, LoginSideEffect> = container(initialState = LoginState())

    private var loggingJob: Job = Job()

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
                        onLogInUseCase(LoginPasswordModel(login, password))
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
