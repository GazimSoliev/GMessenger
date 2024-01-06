package com.gazim.gmessenger.presentation.features.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.gazim.gmessenger.presentation.common.BaseScreen
import com.gazim.gmessenger.presentation.component.RegistrationComponent
import com.gazim.gmessenger.presentation.features.register.RegisterAction.*
import com.gazim.gmessenger.presentation.features.register.RegisterSideEffect.ToBack
import com.gazim.gmessenger.presentation.features.register.RegisterSideEffect.UnableConnectToServer

class RegisterScreen : BaseScreen<RegisterState, RegisterSideEffect, RegisterAction, RegisterViewModel>(
    RegisterViewModel::class,
) {
    private lateinit var snackbarHostState: SnackbarHostState

    override suspend fun handleSideEffect(sideEffect: RegisterSideEffect) {
        when (sideEffect) {
            is ToBack -> navigator.pop()
            is UnableConnectToServer -> snackbarHostState.showSnackbar("Unable connect to server")
        }
    }

    @Composable
    override fun Screen() {
        snackbarHostState = remember { SnackbarHostState() }
        RegistrationComponent(
            modifier = Modifier.fillMaxSize(),
            nickname = state.nickname,
            username = state.username,
            login = state.login,
            password = state.password,
            isWrongNickname = state.isWrongNickname,
            isWrongUsername = state.isWrongUsername,
            isWrongLogin = state.isWrongLogin,
            isWrongPassword = state.isWrongPassword,
            passwordVisibility = state.passwordVisibility,
            showPasswordVisibilityButton = state.showVisibilityButton,
            onNicknameChange = { sendAction(OnChangeNickname(it)) },
            onUsernameChange = { sendAction(OnChangeUsername(it)) },
            onLoginChange = { sendAction(OnChangeLogin(it)) },
            onPasswordChange = { sendAction(OnChangePassword(it)) },
            onClickRegistration = { sendAction(OnRegisterClick) },
            onClickPasswordVisibility = { sendAction(OnPasswordVisibilityClick) },
            back = { sendAction(OnBackClick) },
            registrationInProgress = state.registrationInProgress,
            cancel = { sendAction(CancelRegistration) },
            snackbarHostState = snackbarHostState,
        )
    }
}
