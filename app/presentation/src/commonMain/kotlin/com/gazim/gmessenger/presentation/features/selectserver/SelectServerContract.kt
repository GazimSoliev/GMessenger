package com.gazim.gmessenger.presentation.features.selectserver

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState
import com.gazim.gmessenger.presentation.model.ServerInfoUI

data class SelectServerState(
    val serverList: List<ServerInfoUI> = emptyList(),
    val serverValue: TextFieldValue = TextFieldValue(),
    val hostValue: TextFieldValue = TextFieldValue(),
    val isSecure: Boolean = false,
    val editableMode: Boolean = false,
) : IState

interface SelectServerSideEffect : ISideEffect {
    data object Back : SelectServerSideEffect
}

sealed interface SelectServerAction : IAction {
    data class OnServerClick(
        val server: ServerInfoUI,
    ) : SelectServerAction

    data object OnSelectClick : SelectServerAction

    data object OnAddServerClick : SelectServerAction

    data class OnServerChange(
        val value: TextFieldValue,
    ) : SelectServerAction

    data class OnHostChange(
        val value: TextFieldValue,
    ) : SelectServerAction

    data object OnSaveClick : SelectServerAction

    data object OnCancelClick : SelectServerAction

    data object OnBackClick : SelectServerAction

    data object OnSecureChange : SelectServerAction
}
