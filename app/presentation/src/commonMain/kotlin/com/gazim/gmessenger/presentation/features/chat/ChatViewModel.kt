package com.gazim.gmessenger.presentation.features.chat

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.model.IChatWebSocketModel
import com.gazim.gmessenger.domain.model.IMessageModel
import com.gazim.gmessenger.domain.usecase.IGetChatUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.chat.ChatAction.*
import com.gazim.gmessenger.presentation.features.chat.ChatSideEffect.ToBack
import com.gazim.gmessenger.presentation.model.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

typealias IntentScope = SimpleSyntax<ChatState, ChatSideEffect>

// todo: take out functions
class ChatViewModel(
    private val getChatUseCase: IGetChatUseCase,
) : BaseViewModel<ChatState, ChatSideEffect, ChatAction>() {
    private lateinit var chatModel: IChatWebSocketModel
    private val ms = mutableListOf<IMessageModel>()
    override val container: Container<ChatState, ChatSideEffect> = container(ChatState())

    override fun handleAction(action: ChatAction) {
        intent {
            when (action) {
                is OnStart -> loadChat(action.chat.toChatModel())
                is OnStop -> viewModelScope.launch { chatModel.close() }
                is OnMessageChange -> reduce { state.copy(message = action.message) }
                is OnSendMessage -> {
                    sendMessage(state.message.text)
                    reduce { state.copy(message = TextFieldValue()) }
                }

                is OnBack -> {
                    postSideEffect(ToBack)
                    destroyViewModel()
                }
            }
        }
    }

    private suspend fun IntentScope.loadChat(chat: IChatModel) {
        chatModel = getChatUseCase(chat)
        viewModelScope.launch {
            reduce { state.copy(chatTitle = chatModel.chatName) }
        }
        viewModelScope.launch {
            chatModel.messages.collectLatest { m ->
                ms.add(0, m)
                val groupedMessages = ms.groupBy({ GroupedMessagesDateUI(it.sentAt.date) }, { it.toMessageUI() })
                val mutableList = mutableListOf<IMessageItemUI>()
                groupedMessages.forEach {
                    mutableList.addAll(it.value)
                    mutableList.add(it.key)
                }
                reduce { state.copy(messages = mutableList) }
            }
        }
        openConnection()
    }

    private suspend fun IntentScope.sendMessage(message: String) {
        runCatching {
            chatModel.sendMessage(SentMessageUI(message = message).toSentMessageUI())
        }.onFailure(Throwable::printStackTrace)
    }

    private suspend fun IntentScope.openConnection() {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, e ->
                e.printStackTrace()
                if (e is CancellationException) return@CoroutineExceptionHandler
                viewModelScope.launch {
                    reduce { state.copy(showReconnectionTimer = true, reconnectionTimerSeconds = 5) }
                    for (count in 5 downTo 0) {
                        delay(1000)
                        reduce { state.copy(reconnectionTimerSeconds = count) }
                    }
                    openConnection()
                    reduce { state.copy(showReconnectionTimer = false) }
                }
            },
        ) {
            chatModel.openConnection()
        }
    }
}
