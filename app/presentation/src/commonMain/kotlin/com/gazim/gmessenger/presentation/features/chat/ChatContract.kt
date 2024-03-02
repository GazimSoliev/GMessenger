package com.gazim.gmessenger.presentation.features.chat

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState
import com.gazim.gmessenger.presentation.model.IChatUI
import com.gazim.gmessenger.presentation.model.IMessageItemUI

data class ChatState(
    val chatTitle: String = "",
    val messages: List<IMessageItemUI> = emptyList(),
    val message: TextFieldValue = TextFieldValue(),
    val showReconnectionTimer: Boolean = false,
    val reconnectionTimerSeconds: Int = 0,
) : IState

sealed interface ChatSideEffect : ISideEffect {
    data object ToBack : ChatSideEffect
}

sealed interface ChatAction : IAction {
    data class OnMessageChange(val message: TextFieldValue) : ChatAction

    data object OnSendMessage : ChatAction

    data object OnBack : ChatAction

    data class OnStart(val chat: IChatUI) : ChatAction

    data object OnStop : ChatAction
}
