package com.gazim.gmessenger.presentation.features.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.koin.getScreenModel
import com.gazim.gmessenger.presentation.common.BaseScreen
import com.gazim.gmessenger.presentation.component.ChatComponent
import com.gazim.gmessenger.presentation.features.chat.ChatAction.*
import com.gazim.gmessenger.presentation.features.chat.ChatSideEffect.FollowMessage
import com.gazim.gmessenger.presentation.features.chat.ChatSideEffect.ToBack
import com.gazim.gmessenger.presentation.model.IChatUI
import kotlinx.coroutines.delay

class ChatScreen(
    private val chat: IChatUI,
) : BaseScreen<ChatState, ChatSideEffect, ChatAction, ChatViewModel>() {
    private val lazyListState = LazyListState()

    override suspend fun handleSideEffect(sideEffect: ChatSideEffect) {
        when (sideEffect) {
            is ToBack -> navigator.pop()
            FollowMessage -> {
                delay(100)
                lazyListState.animateScrollToItem(0)
            }
        }
    }

    @Composable
    override fun createViewModel(): ChatViewModel = getScreenModel<ChatViewModel>()

    @Composable
    override fun Screen() {
        ChatComponent(
            modifier = Modifier.fillMaxSize(),
            lazyListState = lazyListState,
            chatTitle = state.chatTitle,
            messages = state.messages,
            message = state.message,
            showReconnectScreen = state.showReconnectionTimer,
            reconnectionTimerSeconds = state.reconnectionTimerSeconds,
            onMessageChange = { sendAction(OnMessageChange(it)) },
            sendMsg = { sendAction(OnSendMessage) },
            back = { sendAction(OnBack) },
            onFollowMessage = { sendAction(OnFollowMessage(it)) },
        )
    }

    override fun onStart() = sendAction(OnStart(chat))

    override fun onStop() = sendAction(OnStop)
}
