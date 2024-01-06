package com.gazim.gmessenger.presentation.features.register

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.register.RegisterAction.*
import com.gazim.gmessenger.presentation.features.register.RegisterSideEffect.ToBack
import com.gazim.gmessenger.presentation.features.register.RegisterSideEffect.UnableConnectToServer
import com.gazim.gmessenger.domain.model.AccountModel
import com.gazim.gmessenger.domain.usecase.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

private typealias IntentScope = SimpleSyntax<RegisterState, RegisterSideEffect>

class RegisterViewModel(
    private val validatePassword: IValidatePassword,
    private val validateLogin: IValidateLogin,
    private val validateNickname: IValidateNickname,
    private val validateUsername: IValidateUsername,
    private val onRegisterUseCase: IOnRegisterUseCase,
) : BaseViewModel<RegisterState, RegisterSideEffect, RegisterAction>() {
    override val container: Container<RegisterState, RegisterSideEffect> = container(initialState = RegisterState())

    private var registrationJob: Job = Job()

    override fun handleAction(action: RegisterAction) {
        intent {
            when (action) {
                is OnBackClick -> backClick()
                is OnChangeNickname -> changeNickname(nickname = action.nickname)
                is OnChangeUsername -> changeUsername(username = action.username)
                is OnChangeLogin -> changeLogin(login = action.login)
                is OnChangePassword -> changePassword(password = action.password)
                is OnPasswordVisibilityClick -> changeVisibility()
                is OnRegisterClick ->
                    register(
                        nickname = state.nickname,
                        username = state.username,
                        login = state.login,
                        password = state.password,
                    )

                is CancelRegistration -> registrationJob.cancel()
            }
        }
    }

    private suspend fun IntentScope.register(
        nickname: TextFieldValue,
        username: TextFieldValue,
        login: TextFieldValue,
        password: TextFieldValue,
    ) {
        reduce { state.copy(registrationInProgress = true) }
        val isWrongNickname = !validateNickname(state.nickname.text)
        val isWrongUsername = !validateUsername(state.username.text)
        val isWrongLogin = !validateLogin(state.login.text)
        val isWrongPassword = !validatePassword(state.password.text)
        if (isWrongNickname || isWrongUsername || isWrongLogin || isWrongPassword) {
            reduce {
                state.copy(
                    isWrongNickname = isWrongNickname,
                    isWrongUsername = isWrongUsername,
                    isWrongLogin = isWrongLogin,
                    isWrongPassword = isWrongPassword,
                )
            }
        } else {
            coroutineScope {
                registrationJob =
                    launch {
                        runCatching {
                            onRegisterUseCase(
                                AccountModel(
                                    nickname = nickname.text,
                                    username = username.text,
                                    login = login.text,
                                    password = password.text,
                                ),
                            )
                        }.onFailure {
                            if (it is CancellationException) return@onFailure
                            postSideEffect(UnableConnectToServer)
                            it.printStackTrace()
                        }.onSuccess {
                            if (it) backClick()
                        }
                    }
            }
        }
        reduce { state.copy(registrationInProgress = false) }
    }

    private suspend fun IntentScope.changeNickname(nickname: TextFieldValue) {
        reduce { state.copy(nickname = nickname) }
        if (validateNickname(nickname.text)) reduce { state.copy(isWrongNickname = false) }
    }

    private suspend fun IntentScope.changeUsername(username: TextFieldValue) {
        reduce { state.copy(username = username) }
        if (validateUsername(username.text)) reduce { state.copy(isWrongUsername = false) }
    }

    private suspend fun IntentScope.changeLogin(login: TextFieldValue) {
        reduce { state.copy(login = login) }
        if (validateLogin(login.text)) reduce { state.copy(isWrongLogin = false) }
    }

    private suspend fun IntentScope.changePassword(password: TextFieldValue) {
        reduce { state.copy(password = password, showVisibilityButton = password.text.isNotEmpty()) }
        if (validatePassword(password.text)) reduce { state.copy(isWrongPassword = false) }
    }

    private suspend fun IntentScope.changeVisibility() {
        reduce { state.copy(passwordVisibility = !state.passwordVisibility) }
    }

    private suspend fun IntentScope.backClick() {
        postSideEffect(ToBack)
        destroyViewModel()
    }
}
