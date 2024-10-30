package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@ExperimentalUuidApi
class ChatService(
    private val chatRepository: IChatRepository,
) : IChatService {
    override suspend fun getChats(userId: Uuid, limit: Int, startFrom: Long?): List<IChat> = chatRepository.getChats(userId)

    override suspend fun getMembers(userId: Uuid, chatId: Uuid): List<User> = chatRepository.getMembers(userId, chatId)

    override suspend fun createChat(userIds: List<Uuid>): IChat? = chatRepository.createChat(userIds)

    override suspend fun getChat(userId: Uuid, chatId: Uuid): IChat? = chatRepository.getChat(userId = userId, chatId = chatId)
}
