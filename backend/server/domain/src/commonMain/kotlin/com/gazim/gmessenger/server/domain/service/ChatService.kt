package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.IChatRepository
import java.util.*

class ChatService(
    private val chatRepository: IChatRepository,
) : IChatService {
    override suspend fun getChats(
        user: User,
        limit: Int,
        startFrom: Long?,
    ): List<IChat> = chatRepository.getChats(user)

    override suspend fun getMembers(
        user: User,
        chat: IChat,
    ): List<User> = chatRepository.getMembers(user, chat)

    override suspend fun createChat(user: List<User>): IChat? = chatRepository.createChat(user)

    override suspend fun getChat(
        user: User,
        chatId: UUID,
    ): IChat? = chatRepository.getChat(user, chatId)
}
