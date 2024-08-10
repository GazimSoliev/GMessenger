package com.gazim.gmessenger.presentation.features.selectserver

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.gazim.gmessenger.domain.usecase.PingUseCase
import com.gazim.gmessenger.presentation.common.collectAsState
import com.gazim.gmessenger.presentation.common.handleSideEffect
import com.gazim.gmessenger.presentation.common.sendAction
import kotlinx.coroutines.cancel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SelectServerScreen(navController: NavHostController) {
    val viewModel = koinViewModel<SelectServerViewModel>()
    val state by viewModel.collectAsState()
    val pingUseCase = koinInject<PingUseCase>()
    viewModel.handleSideEffect { sideEffect ->
        when (sideEffect) {
            is SelectServerSideEffect.Back -> navController.popBackStack()
        }
        cancel()
    }
    SelectServerComposition(
        servers = state.serverList,
        editableMode = state.editableMode,
        serverTextField = state.serverValue,
        hostTextField = state.hostValue,
        isSecure = state.isSecure,
        onServerClick = { viewModel.sendAction(SelectServerAction.OnServerClick(it)) },
        onAddServerClick = { viewModel.sendAction(SelectServerAction.OnAddServerClick) },
        onSelectClick = { viewModel.sendAction(SelectServerAction.OnSelectClick) },
        onServerChange = { viewModel.sendAction(SelectServerAction.OnServerChange(it)) },
        onHostChange = { viewModel.sendAction(SelectServerAction.OnHostChange(it)) },
        onSecureChange = { viewModel.sendAction(SelectServerAction.OnSecureChange) },
        onSaveClick = { viewModel.sendAction(SelectServerAction.OnSaveClick) },
        onCancelClick = { viewModel.sendAction(SelectServerAction.OnCancelClick) },
        onBackClick = { viewModel.sendAction(SelectServerAction.OnBackClick) },
        pinging = { host, isSecure -> pingUseCase(host, isSecure).toString() },
    )
}
