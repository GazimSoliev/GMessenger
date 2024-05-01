package com.gazim.gmessenger.presentation.features.login

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState
import com.gazim.gmessenger.presentation.model.ServerInfoUI

data class LoginState(
    val login: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val passwordVisibility: Boolean = false,
    val showPasswordVisibilityButton: Boolean = false,
    val isError: Boolean = false,
    val loggingInProgress: Boolean = false,
    val servers: List<ServerInfoUI> = emptyList(),
    val dialogIsOpened: Boolean = false,
) : IState

sealed interface LoginSideEffect : ISideEffect {
    data class ToChatsScreen(val session: String) : LoginSideEffect

    data object ToRegisterScreen : LoginSideEffect

    data object UnableConnectToServer : LoginSideEffect

    data object WrongLoginOrPassword : LoginSideEffect
}

sealed interface LoginAction : IAction {
    data class OnChangeLogin(val login: TextFieldValue) : LoginAction

    data class OnChangePassword(val password: TextFieldValue) : LoginAction

    data object OnLogInClick : LoginAction

    data object OnRegisterClick : LoginAction

    data object OnPasswordVisibilityClick : LoginAction

    data object CancelLoggingIn : LoginAction

    data object CloseDialog : LoginAction

    data object OpenDialog : LoginAction
}
