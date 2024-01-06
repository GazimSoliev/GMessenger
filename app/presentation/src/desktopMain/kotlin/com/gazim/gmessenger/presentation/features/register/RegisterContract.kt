package com.gazim.gmessenger.presentation.features.register

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState

data class RegisterState(
    val nickname: TextFieldValue = TextFieldValue(),
    val username: TextFieldValue = TextFieldValue(),
    val login: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val isWrongNickname: Boolean = false,
    val isWrongUsername: Boolean = false,
    val isWrongLogin: Boolean = false,
    val isWrongPassword: Boolean = false,
    val passwordVisibility: Boolean = false,
    val showVisibilityButton: Boolean = false,
    val isError: Boolean = false,
    val registrationInProgress: Boolean = false,
) : IState

sealed interface RegisterSideEffect : ISideEffect {
    object ToBack : RegisterSideEffect

    object UnableConnectToServer : RegisterSideEffect
}

sealed interface RegisterAction : IAction {
    data class OnChangeNickname(val nickname: TextFieldValue) : RegisterAction

    data class OnChangeUsername(val username: TextFieldValue) : RegisterAction

    data class OnChangeLogin(val login: TextFieldValue) : RegisterAction

    data class OnChangePassword(val password: TextFieldValue) : RegisterAction

    object OnPasswordVisibilityClick : RegisterAction

    object OnRegisterClick : RegisterAction

    object OnBackClick : RegisterAction

    object CancelRegistration : RegisterAction
}
