package com.gazim.gmessenger.presentation.features.chat

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.model.IChatWebSocketModel
import com.gazim.gmessenger.domain.model.IMessageModel
import com.gazim.gmessenger.domain.usecase.IGetChatUseCase
import com.gazim.gmessenger.domain.usecase.IGetMessagesUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.chat.ChatAction.*
import com.gazim.gmessenger.presentation.features.chat.ChatSideEffect.ToBack
import com.gazim.gmessenger.presentation.model.SentMessageUI
import com.gazim.gmessenger.presentation.model.toChatModel
import com.gazim.gmessenger.presentation.model.toSentMessageUI
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
    private val getMessages: IGetMessagesUseCase,
) : BaseViewModel<ChatState, ChatSideEffect, ChatAction>() {
    private lateinit var chatModel: IChatWebSocketModel
    private lateinit var pagingSource: MessagePagerSource
    private var followMessage = false
    private val errors: Channel<Throwable> = Channel(Channel.UNLIMITED)
    private val ms = Channel<IMessageModel>(Channel.UNLIMITED)
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

                is OnFollowMessage -> {
                    followMessage = action.value
                }
            }
        }
    }

    private suspend fun IntentScope.loadChat(chat: IChatModel) {
        defineValues(chat)
        setPaging(chat)
        launchCollectingMessages()
        openConnection()
    }

    private suspend fun defineValues(chat: IChatModel) {
        chatModel = getChatUseCase(chat)
    }

    private suspend fun IntentScope.setPaging(chat: IChatModel) {
        viewModelScope.launch {
            for (e in errors) {
                e.printStackTrace()
                delay(5_000)
                pagingSource.invalidate()
            }
        }
        reduce {
            state.copy(
                chatTitle = chatModel.chatName,
                messages =
                Pager(
                    config = PagingConfig(20),
                    pagingSourceFactory = {
                        pagingSource = MessagePagerSource(chat, getMessages, ms, errors); pagingSource
                    },
                ).flow
            )
        }
    }

    private fun IntentScope.launchCollectingMessages() {
        viewModelScope.launch {
            chatModel.messages.collect {
                ms.send(it)
                if (followMessage) postSideEffect(ChatSideEffect.FollowMessage)
            }
        }
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
                    showReconnection()
                    openConnection()
                }
            },
        ) {
            chatModel.openConnection()
        }
    }

    private suspend fun IntentScope.showReconnection() {
        reduce { state.copy(showReconnectionTimer = true, reconnectionTimerSeconds = 5) }
        for (count in 5 downTo 0) {
            delay(1000)
            reduce { state.copy(reconnectionTimerSeconds = count) }
        }
        reduce { state.copy(showReconnectionTimer = false) }
    }
}
