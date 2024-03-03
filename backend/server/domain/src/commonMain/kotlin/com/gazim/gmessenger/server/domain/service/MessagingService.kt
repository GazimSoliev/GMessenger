package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import com.gazim.gmessenger.server.domain.repository.IMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MessagingService(
    private val messageRepository: IMessageRepository,
    private val chatRepository: IChatRepository,
) : IMessagingService {
    private val chatsFlow = mutableMapOf<IChat, MutableSharedFlow<Message>>()

    override suspend fun sendMessage(
        user: User,
        chat: IChat,
        messageForm: MessageForm,
    ) {
        val message = messageRepository.sendMessage(user, chat, messageForm)
        chatsFlow[chat].also { println("Sent 30: $it") }?.emit(message)
        chatsFlow.also { println("Sent 31: $it") }
    }

    override suspend fun getMessages(
        user: User,
        chat: IChat,
        limit: Int,
        startFrom: Long?,
    ): List<Message> = messageRepository.getMessages(user, chat, limit, startFrom)

    override suspend fun getMessageFlow(
        user: User,
        chat: IChat,
    ): Flow<Message>? {
        if (!chatRepository.existInChat(user, chat).also { println("Sent 46: $it") }) return null
        val flow = chatsFlow[chat] ?: MutableSharedFlow<Message>().also { chatsFlow[chat] = it }
        flow.also { println("Sent 48: $it") }
        return flow.asSharedFlow()
    }
}
