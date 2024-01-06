package com.gazim.gmessenger.presentation.features.chat

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState
import com.gazim.gmessenger.presentation.model.IMessageItemUI

data class ChatState(
    val chatTitle: String = "",
    val messages: List<IMessageItemUI> = emptyList(),
    val message: TextFieldValue = TextFieldValue(),
    val showReconnectionTimer: Boolean = false,
    val reconnectionTimerSeconds: Int = 0,
) : IState

sealed interface ChatSideEffect : ISideEffect {
    object ToBack : ChatSideEffect
}

sealed interface ChatAction : IAction {
    data class OnMessageChange(val message: TextFieldValue) : ChatAction

    object OnSendMessage : ChatAction

    object OnBack : ChatAction

    object OnStart : ChatAction

    object OnStop : ChatAction
}
