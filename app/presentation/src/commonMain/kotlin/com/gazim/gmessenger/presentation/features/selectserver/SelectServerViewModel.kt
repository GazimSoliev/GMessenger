package com.gazim.gmessenger.presentation.features.selectserver

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.domain.model.GMessengerServer
import com.gazim.gmessenger.domain.usecase.AddServerUseCase
import com.gazim.gmessenger.domain.usecase.GetAvailableServersUseCase
import com.gazim.gmessenger.domain.usecase.SelectServerUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.model.ServerInfoUI
import com.gazim.gmessenger.presentation.model.toUI
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

private typealias IntentScope = SimpleSyntax<SelectServerState, SelectServerSideEffect>

class SelectServerViewModel(
    private val getAvailableServersUseCase: GetAvailableServersUseCase,
    private val addServerUseCase: AddServerUseCase,
    private val selectServerUseCase: SelectServerUseCase
) : BaseViewModel<SelectServerState, SelectServerSideEffect, SelectServerAction>() {
    private var originServers = emptyList<GMessengerServer>()

    override val container: Container<SelectServerState, SelectServerSideEffect> = container(SelectServerState()) {
        updateServerList()
    }

    private suspend fun IntentScope.updateServerList() {
        val servers = getAvailableServersUseCase()
        originServers = servers
        reduce {
            state.copy(
                serverList = servers.toUI()
            )
        }
    }

    override fun handleAction(action: SelectServerAction) {
        intent {
            when (action) {
                is SelectServerAction.OnAddServerClick -> reduce { state.copy(editableMode = true) }
                is SelectServerAction.OnBackClick -> postSideEffect(SelectServerSideEffect.Back)
                is SelectServerAction.OnCancelClick -> reduce { state.copy(editableMode = false) }
                is SelectServerAction.OnSaveClick -> onSaveClick()
                is SelectServerAction.OnSelectClick -> onSelectClick()
                is SelectServerAction.OnServerChange -> reduce { state.copy(serverValue = action.value) }
                is SelectServerAction.OnServerClick -> onServerClick(action.server)
                is SelectServerAction.OnHostChange -> reduce { state.copy(hostValue = action.value) }
                is SelectServerAction.OnSecureChange -> reduce { state.copy(isSecure = !state.isSecure) }
            }
        }
    }

    private suspend fun IntentScope.onSelectClick() {
        val selectedServer = state.serverList.find { it.selected }?.let { originServers[it.id] } ?: return
        selectServerUseCase(selectedServer)
        postSideEffect(SelectServerSideEffect.Back)
    }

    private suspend fun IntentScope.onServerClick(server: ServerInfoUI) {
        val list = state.serverList.map { if (server == it) it.copy(selected = true) else it.copy(selected = false) }
        reduce { state.copy(serverList = list) }
    }

    private suspend fun IntentScope.onSaveClick() {
        val server = state.serverValue.text
        val url = state.hostValue.text
        val isSecure = state.isSecure
        addServerUseCase(GMessengerServer(url, isSecure, server))
        updateServerList()
        reduce { state.copy(editableMode = false, serverValue = TextFieldValue(), hostValue = TextFieldValue()) }
    }
}