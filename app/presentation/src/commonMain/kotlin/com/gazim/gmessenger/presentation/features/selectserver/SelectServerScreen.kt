package com.gazim.gmessenger.presentation.features.selectserver

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.koin.getScreenModel
import com.gazim.gmessenger.domain.usecase.PingUseCase
import com.gazim.gmessenger.presentation.common.BaseScreen
import org.koin.compose.koinInject

class SelectServerScreen :
    BaseScreen<SelectServerState, SelectServerSideEffect, SelectServerAction, SelectServerViewModel>() {
    override suspend fun handleSideEffect(sideEffect: SelectServerSideEffect) {
        when (sideEffect) {
            is SelectServerSideEffect.Back -> navigator.pop()
        }
    }

    @Composable
    override fun createViewModel() = getScreenModel<SelectServerViewModel>()

    @Composable
    override fun Screen() {
        val pingUseCase = koinInject<PingUseCase>()
        SelectServerComposition(
            servers = state.serverList,
            editableMode = state.editableMode,
            serverTextField = state.serverValue,
            hostTextField = state.hostValue,
            isSecure = state.isSecure,
            onServerClick = { sendAction(SelectServerAction.OnServerClick(it)) },
            onAddServerClick = { sendAction(SelectServerAction.OnAddServerClick) },
            onSelectClick = { sendAction(SelectServerAction.OnSelectClick) },
            onServerChange = { sendAction(SelectServerAction.OnServerChange(it)) },
            onHostChange = { sendAction(SelectServerAction.OnHostChange(it)) },
            onSecureChange = { sendAction(SelectServerAction.OnSecureChange) },
            onSaveClick = { sendAction(SelectServerAction.OnSaveClick) },
            onCancelClick = { sendAction(SelectServerAction.OnCancelClick) },
            onBackClick = { sendAction(SelectServerAction.OnBackClick) },
            pinging = { host, isSecure -> pingUseCase(host, isSecure).toString() }
        )
    }
}