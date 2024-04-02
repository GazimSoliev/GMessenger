package com.gazim.gmessenger.presentation.features.chat

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.paging.PagingData
import com.gazim.gmessenger.presentation.common.IAction
import com.gazim.gmessenger.presentation.common.ISideEffect
import com.gazim.gmessenger.presentation.common.IState
import com.gazim.gmessenger.presentation.model.IChatUI
import com.gazim.gmessenger.presentation.model.IMessageItemUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ChatState(
    val chatTitle: String = "",
    val messages: Flow<PagingData<IMessageItemUI>> = emptyFlow(),
    val message: TextFieldValue = TextFieldValue(),
    val showReconnectionTimer: Boolean = false,
    val reconnectionTimerSeconds: Int = 0,
) : IState

sealed interface ChatSideEffect : ISideEffect {
    data object ToBack : ChatSideEffect

    data object FollowMessage : ChatSideEffect
}

sealed interface ChatAction : IAction {
    data class OnMessageChange(val message: TextFieldValue) : ChatAction

    data object OnSendMessage : ChatAction

    data object OnBack : ChatAction

    data class OnStart(val chat: IChatUI) : ChatAction

    data object OnStop : ChatAction

    data class OnFollowMessage(val value: Boolean) : ChatAction
}
