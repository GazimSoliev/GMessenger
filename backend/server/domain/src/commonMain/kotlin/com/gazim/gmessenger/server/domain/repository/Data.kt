package com.gazim.gmessenger.server.domain.repository

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.datetime.LocalDateTime

interface IUserRepository {
    suspend fun insert(user: IUser): Boolean

    suspend fun findByUsername(
        username: String,
        limit: Int,
    ): List<IUser>

    suspend fun getUser(idToken: Int): IUser
}

interface ILoginRegisterRepository {
    suspend fun login(
        loginPassword: ILoginPassword,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Int?

    suspend fun register(account: IAccount): Boolean
}

interface IChatRepository {
    suspend fun getChats(user: IUser): List<IChat>

    suspend fun getMembers(
        user: IUser,
        chat: IChat,
    ): List<IUser>

    suspend fun createChat(users: List<IUser>): IChat?

    suspend fun getChat(
        user: IUser,
        chatId: Int,
    ): IChat?

    suspend fun existInChat(
        user: IUser,
        chat: IChat,
    ): Boolean
}

interface IMessageRepository {
    suspend fun getMessages(
        user: IUser,
        chat: IChat,
        limit: Int = 64,
        startFrom: Long? = null,
    ): List<IMessage>

    suspend fun sendMessage(
        user: IUser,
        chat: IChat,
        message: IMessage,
    ): Boolean
}
