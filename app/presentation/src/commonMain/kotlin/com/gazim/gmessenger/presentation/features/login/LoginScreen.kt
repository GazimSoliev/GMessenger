package com.gazim.gmessenger.presentation.features.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.gazim.gmessenger.presentation.common.collectAsState
import com.gazim.gmessenger.presentation.common.handleSideEffect
import com.gazim.gmessenger.presentation.common.sendAction
import com.gazim.gmessenger.presentation.features.login.LoginAction.*
import com.gazim.gmessenger.presentation.features.login.LoginSideEffect.*
import com.gazim.gmessenger.presentation.navigation.Screen
import com.gazim.gmessenger.presentation.navigation.navigate
import com.gazim.gmessenger.presentation.navigation.replace
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = koinViewModel<LoginViewModel>()
    val state by viewModel.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    viewModel.handleSideEffect { sideEffect ->
        when (sideEffect) {
            is UnableConnectToServer ->
                launch {
                    snackBarHostState.showSnackbar("Unable connect to server")
                }

            is WrongLoginOrPassword ->
                launch {
                    snackBarHostState.showSnackbar("Wrong login or password")
                }
            is ToChatsScreen -> navController.replace(Screen.Chats)
            is ToRegisterScreen -> navController.navigate(Screen.Registration)
            is ToSelectServerScreen -> navController.navigate(Screen.SelectServer)
        }
    }
    LoginComposition(
        modifier = Modifier.fillMaxSize(),
        login = state.login,
        password = state.password,
        passwordVisibility = state.passwordVisibility,
        showPasswordVisibilityButton = state.showPasswordVisibilityButton,
        onLoginChange = { viewModel.sendAction(OnChangeLogin(it)) },
        onPasswordChange = { viewModel.sendAction(OnChangePassword(it)) },
        onClickLogIn = { viewModel.sendAction(OnLogInClick) },
        onRegister = { viewModel.sendAction(OnRegisterClick) },
        onClickPasswordVisibility = { viewModel.sendAction(OnPasswordVisibilityClick) },
        snackbarHostState = snackBarHostState,
        loggingInProgress = state.loggingInProgress,
        cancel = { viewModel.sendAction(CancelLoggingIn) },
        onSelectServerClick = { viewModel.sendAction(OnSelectServerClick) },
    )
}
