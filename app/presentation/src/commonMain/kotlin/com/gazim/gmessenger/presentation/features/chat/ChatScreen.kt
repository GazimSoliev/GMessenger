package com.gazim.gmessenger.presentation.features.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.gazim.gmessenger.presentation.common.collectAsState
import com.gazim.gmessenger.presentation.common.handleSideEffect
import com.gazim.gmessenger.presentation.common.sendAction
import com.gazim.gmessenger.presentation.features.chat.ChatAction.*
import com.gazim.gmessenger.presentation.features.chat.ChatSideEffect.FollowMessage
import com.gazim.gmessenger.presentation.features.chat.ChatSideEffect.ToBack
import com.gazim.gmessenger.presentation.model.ChatUI
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ChatScreen(
    navController: NavController,
    chat: ChatUI,
) {
    val viewModel = koinViewModel<ChatViewModel>()
    val state by viewModel.collectAsState()
    val lazyListState = remember { LazyListState() }
    viewModel.handleSideEffect { sideEffect ->
        when (sideEffect) {
            is ToBack -> {
                navController.popBackStack()
                cancel()
            }
            is FollowMessage -> {
                delay(100)
                lazyListState.animateScrollToItem(0)
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.sendAction(OnStart(chat))
    }
    ChatComposition(
        modifier = Modifier.fillMaxSize(),
        lazyListState = lazyListState,
        chatTitle = state.chatTitle,
        imageBitmap = state.imageBitmap,
        messages = state.messages,
        message = state.message,
        showReconnectScreen = state.showReconnectionTimer,
        reconnectionTimerSeconds = state.reconnectionTimerSeconds,
        onMessageChange = { viewModel.sendAction(OnMessageChange(it)) },
        sendMsg = { viewModel.sendAction(OnSendMessage) },
        back = { viewModel.sendAction(OnBack) },
        onFollowMessage = { viewModel.sendAction(OnFollowMessage(it)) },
    )
}
