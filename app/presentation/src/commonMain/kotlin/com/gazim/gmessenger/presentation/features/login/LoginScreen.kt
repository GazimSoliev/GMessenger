package com.gazim.gmessenger.presentation.features.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.gazim.gmessenger.presentation.common.BaseScreen
import com.gazim.gmessenger.presentation.component.LoginComponent
import com.gazim.gmessenger.presentation.features.chats.ChatsScreen
import com.gazim.gmessenger.presentation.features.login.LoginAction.*
import com.gazim.gmessenger.presentation.features.login.LoginSideEffect.*
import com.gazim.gmessenger.presentation.features.register.RegisterScreen

class LoginScreen : BaseScreen<LoginState, LoginSideEffect, LoginAction, LoginViewModel>(LoginViewModel::class) {
    private lateinit var snackbarHostState: SnackbarHostState

    override suspend fun handleSideEffect(sideEffect: LoginSideEffect) {
        when (sideEffect) {
            is ToChatsScreen -> navigator.replace(ChatsScreen())
            is ToRegisterScreen -> navigator.push(RegisterScreen())
            is UnableConnectToServer -> snackbarHostState.showSnackbar("Unable connect to server")
            is WrongLoginOrPassword -> snackbarHostState.showSnackbar("Wrong login or password")
        }
    }

    @Composable
    override fun Screen() {
        snackbarHostState = remember { SnackbarHostState() }
        LoginComponent(
            modifier = Modifier.fillMaxSize(),
            login = state.login,
            password = state.password,
            passwordVisibility = state.passwordVisibility,
            showPasswordVisibilityButton = state.showPasswordVisibilityButton,
            onLoginChange = { sendAction(OnChangeLogin(it)) },
            onPasswordChange = { sendAction(OnChangePassword(it)) },
            onClickLogIn = { sendAction(OnLogInClick) },
            onRegister = { sendAction(OnRegisterClick) },
            onClickPasswordVisibility = { sendAction(OnPasswordVisibilityClick) },
            snackbarHostState = snackbarHostState,
            loggingInProgress = state.loggingInProgress,
            cancel = { sendAction(CancelLoggingIn) },
        )
    }
}
