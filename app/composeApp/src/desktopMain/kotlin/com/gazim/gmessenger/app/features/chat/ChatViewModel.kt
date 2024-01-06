package com.gazim.gmessenger.app.features.chat

import androidx.compose.ui.text.input.TextFieldValue
import com.gazim.gmessenger.app.common.BaseViewModel
import com.gazim.gmessenger.app.features.chat.ChatAction.*
import com.gazim.gmessenger.app.features.chat.ChatSideEffect.ToBack
import com.gazim.gmessenger.app.model.*
import com.gazim.gmessenger.domain.usecase.*
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
    private val openChatUseCase: IOpenChatUseCase,
    private val closeChatUseCase: ICloseChatUseCase,
    private val getMessagesUseCase: IGetMessagesUseCase,
    private val sendMessageUseCase: ISendMessageUseCase,
    private val getChatNameUseCase: IGetChatNameUseCase,
    private val closeChatScopeUseCase: ICloseChatScopeUseCase,
) : BaseViewModel<ChatState, ChatSideEffect, ChatAction>() {
    override val container: Container<ChatState, ChatSideEffect> =
        container(initialState = ChatState()) {
            scope.launch {
                val chatTitle = getChatNameUseCase()
                reduce { state.copy(chatTitle = chatTitle) }
            }
            scope.launch {
                getMessagesUseCase().collectLatest { ms ->
                    val groupedMessages = ms.groupBy({ GroupedMessagesDateUI(it.sentAt.date) }, { it.toMessageUI() })
                    val mutableList = mutableListOf<IMessageItemUI>()
                    groupedMessages.forEach {
                        mutableList.addAll(it.value)
                        mutableList.add(it.key)
                    }
                    reduce { state.copy(messages = mutableList) }
                }
            }
        }

    override fun handleAction(action: ChatAction) {
        intent {
            when (action) {
                is OnStart -> openConnection()
                is OnStop -> scope.launch { closeChatUseCase() }
                is OnMessageChange -> reduce { state.copy(message = action.message) }
                is OnSendMessage -> {
                    sendMessage(state.message.text)
                    reduce { state.copy(message = TextFieldValue()) }
                }

                is OnBack -> {
                    postSideEffect(ToBack)
                    closeChatScopeUseCase()
                    destroyViewModel()
                }
            }
        }
    }

    private suspend fun IntentScope.sendMessage(message: String) {
        runCatching {
            sendMessageUseCase(SentMessageUI(message = message).toSentMessageUI())
        }.onFailure(Throwable::printStackTrace)
    }

    private suspend fun IntentScope.openConnection() {
        scope.launch(
            CoroutineExceptionHandler { _, e ->
                e.printStackTrace()
                if (e is CancellationException) return@CoroutineExceptionHandler
                scope.launch {
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
            openChatUseCase()
        }
    }
}
