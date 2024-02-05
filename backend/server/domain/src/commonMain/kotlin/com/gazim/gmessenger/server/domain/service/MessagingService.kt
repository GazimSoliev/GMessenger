package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.*
import com.gazim.gmessenger.server.domain.repository.IMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.toKotlinLocalDateTime

class MessagingService(
    private val messageRepository: IMessageRepository
) : IMessagingService {
    override suspend fun sendMessage(user: IUser, chat: IChat, sentMessage: ISentMessage): IMessage {
        val message = Message(
            message = sentMessage.message,
            user = user,
            sentAt = java.time.LocalDateTime.now().toKotlinLocalDateTime()
        )
        messageRepository.sendMessage(user, chat, message)
        return message
    }

    override suspend fun getMessages(user: IUser, chat: IChat, limit: Int, startFrom: Long?): List<IMessage> =
        messageRepository.getMessages(user, chat, limit, startFrom)


    override suspend fun getMessageFlow(user: IUser, chat: IChat): Flow<IMessage> {
        TODO("Not yet implemented")
    }
}