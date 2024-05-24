package com.gazim.gmessenger.presentation.features.selectserver

import com.gazim.gmessenger.domain.usecase.GetAvailableServersUseCase
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
) : BaseViewModel<SelectServerState, SelectServerSideEffect, SelectServerAction>() {
    override val container: Container<SelectServerState, SelectServerSideEffect> = container(SelectServerState()) {
        val servers = getAvailableServersUseCase()
        reduce {
            state.copy(
                serverList = servers.map { it.toUI(5_000) }
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
                is SelectServerAction.OnUrlChange -> reduce { state.copy(urlValue = action.value) }
            }
        }
    }

    private suspend fun IntentScope.onSelectClick() {

    }

    private suspend fun IntentScope.onServerClick(server: ServerInfoUI) {
        val list = state.serverList.map { if (server == it) it.copy(selected = true) else it.copy(selected = false) }
        reduce { state.copy(serverList = list) }
    }

    private suspend fun IntentScope.onSaveClick() {
        val server = state.serverValue.text
        val url = state.urlValue.text

        postSideEffect(SelectServerSideEffect.Back)
    }
}