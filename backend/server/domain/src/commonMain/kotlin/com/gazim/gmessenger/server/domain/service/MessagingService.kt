package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.*
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import com.gazim.gmessenger.server.domain.repository.IMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime.now

class MessagingService(
    private val messageRepository: IMessageRepository,
    private val chatRepository: IChatRepository,
) : IMessagingService {
    private val chatsFlow = mutableMapOf<IChat, MutableSharedFlow<IMessage>>()

    override suspend fun sendMessage(
        user: IUser,
        chat: IChat,
        sentMessage: ISentMessage,
    ): IMessage {
        val message =
            Message(
                message = sentMessage.message,
                user = user,
                sentAt = now().toKotlinLocalDateTime(),
            )
        messageRepository.sendMessage(user, chat, message)
        chatsFlow[chat].also { println("Sent 30: $it") }?.emit(message)
        chatsFlow.also { println("Sent 31: $it") }
        return message
    }

    override suspend fun getMessages(
        user: IUser,
        chat: IChat,
        limit: Int,
        startFrom: Long?,
    ): List<IMessage> = messageRepository.getMessages(user, chat, limit, startFrom)

    override suspend fun getMessageFlow(
        user: IUser,
        chat: IChat,
    ): Flow<IMessage>? {
        if (!chatRepository.existInChat(user, chat).also { println("Sent 46: $it") }) return null
        val flow = chatsFlow[chat] ?: MutableSharedFlow<IMessage>().also { chatsFlow[chat] = it }
        flow.also { println("Sent 48: $it") }
        return flow.asSharedFlow()
    }
}
