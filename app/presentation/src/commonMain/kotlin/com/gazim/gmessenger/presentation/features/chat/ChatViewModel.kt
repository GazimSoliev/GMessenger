package com.gazim.gmessenger.presentation.features.chat

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.map
import app.cash.paging.PagingConfig
import app.cash.paging.cachedIn
import app.cash.paging.insertSeparators
import com.gazim.gmessenger.domain.model.*
import com.gazim.gmessenger.domain.usecase.GetChatUseCase
import com.gazim.gmessenger.domain.usecase.GetImageContentUseCase
import com.gazim.gmessenger.domain.usecase.GetMessagesUseCase
import com.gazim.gmessenger.presentation.common.BaseViewModel
import com.gazim.gmessenger.presentation.features.chat.ChatAction.*
import com.gazim.gmessenger.presentation.features.chat.ChatSideEffect.ToBack
import com.gazim.gmessenger.presentation.model.ChatUI
import com.gazim.gmessenger.presentation.model.GroupedMessagesDateUI
import com.gazim.gmessenger.presentation.model.PrivateChatUI
import com.gazim.gmessenger.presentation.model.toDomain
import com.gazim.gmessenger.presentation.model.toUI
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.Syntax
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
    private val ms = MutableStateFlow(listOf<IMessage>())
    override val container: Container<ChatState, ChatSideEffect> = container(ChatState())

    override fun handleAction(action: ChatAction) {
        intent {
            when (action) {
                is OnStart -> loadChat(action.chat)
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
    private suspend fun IntentScope.loadChat(chat: ChatUI) {
        reduce { state.copy(chatTitle = chat.chatName) }
        if (chat is PrivateChatUI) {
            viewModelScope.launch(Dispatchers.IO) {
                val imageId = chat.user.photo?.id ?: return@launch
                while (true) {
                    val image = getImageContentUseCase(imageId).getOrNull() ?: continue
                    reduce { state.copy(imageBitmap = image.decodeToImageBitmap()) }
                    break
                }
            }
        }
        defineValues(chat.identifier)
        setPaging(chat.identifier)
        launchCollectingMessages()
        openConnection()
    }

    private suspend fun defineValues(chatId: Uuid) {
        getChatUseCase(chatId).onSuccess { chatModel = it }
    }

    private suspend fun IntentScope.setPaging(chatId: Uuid) {
        viewModelScope.launch {
            errors.collectLatest { e ->
                e.printStackTrace()
                delay(5_000)
                pagingSource.invalidate()
            }
        }
        reduce {
            val pageDataFlow = Pager(
                config = PagingConfig(20),
                pagingSourceFactory = {
                    pagingSource = MessagePagerSource(
                        chatId = chatId,
                        getMessages = getMessages,
                        errors = errors
                    )
                    pagingSource
                },
            ).flow.cachedIn(viewModelScope)
            val mappedPageDataFlow = combine(pageDataFlow, ms) { page, messages ->
                val newMessagesPage = messages.fold(page) { data, mes ->
                    data.insertSeparators { before, after ->
                        if (before == null && after != mes) mes
                        else null
                    }
                }
                val mappedNewMessagesPage = newMessagesPage.map { MessageContainer.Message(it) }
                val datedMessagesPage = mappedNewMessagesPage.insertSeparators { before, after ->
                    val sentBefore = before?.message?.sentAt?.date
                    val sentAfter = after?.message?.sentAt?.date
                    when (sentBefore) {
                        sentAfter, null -> null
                        else -> MessageContainer.GroupedMessagesDate(sentBefore)
                    }
                }
                datedMessagesPage.map { messageContainer ->
                    when (messageContainer) {
                        is MessageContainer.Message -> messageContainer.message.toUI()
                        is MessageContainer.GroupedMessagesDate -> GroupedMessagesDateUI(messageContainer.date)
                    }
                }
            }
            state.copy(
                messages = mappedPageDataFlow,
            )
        }
    }

    private sealed interface MessageContainer {
        @JvmInline
        value class Message(val message: IMessage) : MessageContainer

        @JvmInline
        value class GroupedMessagesDate(val date: LocalDate) : MessageContainer
    }

    private fun IntentScope.launchCollectingMessages() {
        viewModelScope.launch {
            chatModel.messages.collect { newMessage ->
                ms.update { messages -> messages + newMessage }
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
