package com.gazim.gmessenger.presentation.features.login

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState

data class LoginState(
    val login: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val passwordVisibility: Boolean = false,
    val showPasswordVisibilityButton: Boolean = false,
    val isError: Boolean = false,
    val loggingInProgress: Boolean = false,
) : IState

sealed interface LoginSideEffect : ISideEffect {
    object ToChatsScreen : LoginSideEffect

    object ToRegisterScreen : LoginSideEffect

    object UnableConnectToServer : LoginSideEffect

    object WrongLoginOrPassword : LoginSideEffect
}

sealed interface LoginAction : IAction {
    data class OnChangeLogin(val login: TextFieldValue) : LoginAction

    data class OnChangePassword(val password: TextFieldValue) : LoginAction

    object OnLogInClick : LoginAction

    object OnRegisterClick : LoginAction

    object OnPasswordVisibilityClick : LoginAction

    object CancelLoggingIn : LoginAction
}
