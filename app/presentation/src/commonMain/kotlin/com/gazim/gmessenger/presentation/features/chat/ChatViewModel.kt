package com.gazim.gmessenger.presentation.features.chat

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import com.gazim.gmessenger.domain.model.*
import com.gazim.gmessenger.domain.usecase.GetChatUseCase
import com.gazim.gmessenger.domain.usecase.GetImageContentUseCase
import com.gazim.gmessenger.domain.usecase.GetMessagesUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.chat.ChatAction.*
import com.gazim.gmessenger.presentation.features.chat.ChatSideEffect.ToBack
import com.gazim.gmessenger.presentation.model.toDomain
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.Syntax
import kotlin.uuid.ExperimentalUuidApi

private typealias IntentScope = Syntax<ChatState, ChatSideEffect>

// todo: take out functions
@OptIn(ExperimentalUuidApi::class)
class ChatViewModel(
    private val getChatUseCase: GetChatUseCase,
    private val getMessages: GetMessagesUseCase,
    private val getImageContentUseCase: GetImageContentUseCase,
) : BaseViewModel<ChatState, ChatSideEffect, ChatAction>() {
    private lateinit var chatModel: IChatWebSocketModel
    private lateinit var pagingSource: MessagePagerSource
    private var followMessage = false
    private val errors = MutableSharedFlow<Throwable>()
    private val ms = Channel<IMessage>(Channel.UNLIMITED)
    override val container: Container<ChatState, ChatSideEffect> = container(ChatState())

    override fun handleAction(action: ChatAction) {
        intent {
            when (action) {
                is OnStart -> loadChat(action.chat.toDomain())
                is OnStop -> viewModelScope.launch { chatModel.close() }
                is OnMessageChange -> reduce { state.copy(message = action.message) }
                is OnSendMessage -> {
                    sendMessage(state.message.text)
                    reduce { state.copy(message = TextFieldValue()) }
                }

                is OnBack -> {
                    postSideEffect(ToBack)
                }

                is OnFollowMessage -> {
                    followMessage = action.value
                }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun IntentScope.loadChat(chat: IChat) {
        if (chat is PrivateChat) {
            viewModelScope.launch(Dispatchers.IO) {
                val imageId = chat.user.photo?.id ?: return@launch
                while (true) {
                    val image = getImageContentUseCase(imageId).getOrNull() ?: continue
                    reduce { state.copy(imageBitmap = image.decodeToImageBitmap()) }
                    break
                }
            }
        }
        defineValues(chat)
        setPaging(chat)
        launchCollectingMessages()
        openConnection()
    }

    private suspend fun defineValues(chat: IChat) {
        getChatUseCase(chat).onSuccess { chatModel = it }
    }

    private suspend fun IntentScope.setPaging(chat: IChat) {
        viewModelScope.launch {
            errors.collectLatest { e ->
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
                            pagingSource = MessagePagerSource(chat, getMessages, ms, errors)
                            pagingSource
                        },
                    ).flow,
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
            chatModel.sendMessage(SentMessage(message = message))
        }.onFailure(Throwable::printStackTrace)
    }

    private fun IntentScope.openConnection() {
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
