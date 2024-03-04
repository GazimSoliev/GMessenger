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
import java.util.*

class MessagingService(
    private val messageRepository: IMessageRepository,
    private val chatRepository: IChatRepository,
) : IMessagingService {
    private val chatsFlow = mutableMapOf<UUID, MutableSharedFlow<Message>>()

    override suspend fun sendMessage(
        user: User,
        chat: IChat,
        messageForm: MessageForm,
    ) {
        val message = messageRepository.sendMessage(user, chat, messageForm)
        chatsFlow[chat.id]?.emit(message)
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
        if (!chatRepository.existInChat(user, chat)) return null
        val flow = chatsFlow[chat.id] ?: MutableSharedFlow<Message>().also { chatsFlow[chat.id] = it }
        return flow.asSharedFlow()
    }
}
