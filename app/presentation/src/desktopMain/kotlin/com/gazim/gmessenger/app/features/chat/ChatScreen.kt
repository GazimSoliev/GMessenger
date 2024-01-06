package com.gazim.gmessenger.app.features.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gazim.gmessenger.app.common.BaseScreen
import com.gazim.gmessenger.app.component.ChatComponent
import com.gazim.gmessenger.app.features.chat.ChatAction.*
import com.gazim.gmessenger.app.features.chat.ChatSideEffect.ToBack

class ChatScreen : BaseScreen<ChatState, ChatSideEffect, ChatAction, ChatViewModel>(ChatViewModel::class) {
    override suspend fun handleSideEffect(sideEffect: ChatSideEffect) {
        when (sideEffect) {
            is ToBack -> navigator.pop()
        }
    }

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

    override fun onStart() = sendAction(OnStart)

    override fun onStop() = sendAction(OnStop)
}
