package com.gazim.gmessenger.presentation.features.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.koin.getScreenModel
import com.gazim.gmessenger.presentation.common.BaseScreen
import com.gazim.gmessenger.presentation.features.register.RegisterAction.*
import com.gazim.gmessenger.presentation.features.register.RegisterSideEffect.ToBack
import com.gazim.gmessenger.presentation.features.register.RegisterSideEffect.UnableConnectToServer

class RegisterScreen : BaseScreen<RegisterState, RegisterSideEffect, RegisterAction, RegisterViewModel>() {
    private lateinit var snackBarHostState: SnackbarHostState

    override suspend fun handleSideEffect(sideEffect: RegisterSideEffect) {
        when (sideEffect) {
            is ToBack -> navigator.pop()
            is UnableConnectToServer -> snackBarHostState.showSnackbar("Unable connect to server")
        }
    }

    @Composable
    override fun createViewModel(): RegisterViewModel = getScreenModel<RegisterViewModel>()

    @Composable
    override fun Screen() {
        snackBarHostState = remember { SnackbarHostState() }
        RegistrationComposition(
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
            snackbarHostState = snackBarHostState,
        )
    }
}
