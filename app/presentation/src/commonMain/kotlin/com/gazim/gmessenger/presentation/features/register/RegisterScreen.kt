package com.gazim.gmessenger.presentation.features.register

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
import com.gazim.gmessenger.presentation.features.register.RegisterAction.*
import com.gazim.gmessenger.presentation.features.register.RegisterSideEffect.ToBack
import com.gazim.gmessenger.presentation.features.register.RegisterSideEffect.UnableConnectToServer
import gmessenger.app.presentation.generated.resources.Res
import gmessenger.app.presentation.generated.resources.unable_connect_to_server
import kotlinx.coroutines.cancel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel = koinViewModel<RegisterViewModel>()
    val state by viewModel.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val strUnableConnectToServer = stringResource(Res.string.unable_connect_to_server)
    viewModel.handleSideEffect { sideEffect ->
        when (sideEffect) {
            is ToBack -> {
                navController.popBackStack()
                cancel()
            }
            is UnableConnectToServer -> snackBarHostState.showSnackbar(strUnableConnectToServer)
        }
    }
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
        onNicknameChange = { viewModel.sendAction(OnChangeNickname(it)) },
        onUsernameChange = { viewModel.sendAction(OnChangeUsername(it)) },
        onLoginChange = { viewModel.sendAction(OnChangeLogin(it)) },
        onPasswordChange = { viewModel.sendAction(OnChangePassword(it)) },
        onClickRegistration = { viewModel.sendAction(OnRegisterClick) },
        onClickPasswordVisibility = { viewModel.sendAction(OnPasswordVisibilityClick) },
        back = { viewModel.sendAction(OnBackClick) },
        registrationInProgress = state.registrationInProgress,
        cancel = { viewModel.sendAction(CancelRegistration) },
        snackbarHostState = snackBarHostState,
    )
}
