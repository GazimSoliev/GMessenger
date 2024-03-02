package com.gazim.gmessenger.presentation.features.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.koin.getScreenModel
import com.gazim.gmessenger.presentation.common.BaseScreen
import com.gazim.gmessenger.presentation.component.ChatComponent
import com.gazim.gmessenger.presentation.features.chat.ChatAction.*
import com.gazim.gmessenger.presentation.features.chat.ChatSideEffect.ToBack
import com.gazim.gmessenger.presentation.model.IChatUI

class ChatScreen(
    private val chat: IChatUI,
) : BaseScreen<ChatState, ChatSideEffect, ChatAction, ChatViewModel>() {
    override suspend fun handleSideEffect(sideEffect: ChatSideEffect) {
        when (sideEffect) {
            is ToBack -> navigator.pop()
        }
    }

    @Composable
    override fun createViewModel(): ChatViewModel = getScreenModel<ChatViewModel>()

    @Composable
    override fun Screen() {
        ChatComponent(
            modifier = Modifier.fillMaxSize(),
            chatTitle = state.chatTitle,
            messages = state.messages,
            message = state.message,
            showReconnectScreen = state.showReconnectionTimer,
            reconnectionTimerSeconds = state.reconnectionTimerSeconds,
            onMessageChange = { sendAction(OnMessageChange(it)) },
            sendMsg = { sendAction(OnSendMessage) },
            back = { sendAction(OnBack) },
        )
    }

    override fun onStart() = sendAction(OnStart(chat))

    override fun onStop() = sendAction(OnStop)
}
