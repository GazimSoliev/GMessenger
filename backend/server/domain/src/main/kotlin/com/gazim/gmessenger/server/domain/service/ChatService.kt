package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import java.util.*

class ChatService(
    private val chatRepository: IChatRepository,
) : IChatService {
    override suspend fun getChats(
        userId: UUID,
        limit: Int,
        startFrom: Long?,
    ): List<IChat> = chatRepository.getChats(userId)

    override suspend fun getMembers(
        userId: UUID,
        chatId: UUID,
    ): List<User> = chatRepository.getMembers(userId, chatId)

    override suspend fun createChat(userIds: List<UUID>): IChat? = chatRepository.createChat(userIds)

    override suspend fun getChat(
        userId: UUID,
        chatId: UUID,
    ): IChat? = chatRepository.getChat(userId = userId, chatId = chatId)
}
