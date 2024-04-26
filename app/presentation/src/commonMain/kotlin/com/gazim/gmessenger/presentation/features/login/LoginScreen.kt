package com.gazim.gmessenger.presentation.features.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.koin.getScreenModel
import com.gazim.gmessenger.di.AccountScope
import com.gazim.gmessenger.presentation.common.BaseScreen
import com.gazim.gmessenger.presentation.features.chats.ChatsScreen
import com.gazim.gmessenger.presentation.features.login.LoginAction.*
import com.gazim.gmessenger.presentation.features.login.LoginSideEffect.*
import com.gazim.gmessenger.presentation.features.register.RegisterScreen
import org.koin.compose.getKoin
import org.koin.core.Koin

class LoginScreen : BaseScreen<LoginState, LoginSideEffect, LoginAction, LoginViewModel>() {
    private lateinit var snackBarHostState: SnackbarHostState
    private lateinit var koin: Koin

    override suspend fun handleSideEffect(sideEffect: LoginSideEffect) {
        when (sideEffect) {
            is ToChatsScreen -> {
                koin.createScope<AccountScope>(scopeId = sideEffect.session)
                navigator.replace(ChatsScreen())
            }
            is ToRegisterScreen -> navigator.push(RegisterScreen())
            is UnableConnectToServer -> snackBarHostState.showSnackbar("Unable connect to server")
            is WrongLoginOrPassword -> snackBarHostState.showSnackbar("Wrong login or password")
        }
    }

    @Composable
    override fun createViewModel(): LoginViewModel = getScreenModel<LoginViewModel>()

    @Composable
    override fun Screen() {
        koin = getKoin()
        snackBarHostState = remember { SnackbarHostState() }
        LoginComposition(
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
            snackbarHostState = snackBarHostState,
            loggingInProgress = state.loggingInProgress,
            cancel = { sendAction(CancelLoggingIn) },
        )
    }
}
