package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.repository.IChatRepository

class ChatService(
    private val chatRepository: IChatRepository
) : IChatService {
    override suspend fun getChats(user: IUser, limit: Int, startFrom: Long?): List<IChat> =
        chatRepository.getChats(user)

    override suspend fun getMembers(user: IUser, chat: IChat): List<IUser> =
        chatRepository.getMembers(user, chat)

    override suspend fun createChat(user: List<IUser>): IChat? =
        chatRepository.createChat(user)

    override suspend fun getChat(user: IUser, chatId: Int): IChat? =
        chatRepository.getChat(user, chatId)
}