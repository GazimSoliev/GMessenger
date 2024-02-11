package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface IUserService {
    suspend fun findUser(username: String): List<IUser>

    suspend fun getUser(idToken: Int): IUser
}

interface IAuthorizationService {
    suspend fun login(
        loginPassword: ILoginPassword,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Int?

    suspend fun register(account: IAccount): Boolean
}

interface IChatService {
    suspend fun getChats(
        user: IUser,
        limit: Int = 64,
        startFrom: Long? = null,
    ): List<IChat>

    suspend fun getMembers(
        user: IUser,
        chat: IChat,
    ): List<IUser>

    suspend fun createChat(user: List<IUser>): IChat?

    suspend fun getChat(
        user: IUser,
        chatId: Int,
    ): IChat?
}

interface IMessagingService {
    suspend fun sendMessage(
        user: IUser,
        chat: IChat,
        sentMessage: ISentMessage,
    ): IMessage

    suspend fun getMessages(
        user: IUser,
        chat: IChat,
        limit: Int = 64,
        startFrom: Long? = null,
    ): List<IMessage>

    suspend fun getMessageFlow(
        user: IUser,
        chat: IChat,
    ): Flow<IMessage>?
}
