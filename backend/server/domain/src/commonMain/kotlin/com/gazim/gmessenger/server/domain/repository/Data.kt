package com.gazim.gmessenger.server.domain.repository

import com.gazim.gmessenger.server.domain.model.*
import java.time.LocalDateTime
import java.util.*

interface IUserRepository {
    suspend fun insert(user: User): Boolean

    suspend fun findByUsername(
        username: String,
        limit: Int,
    ): List<User>

    suspend fun getUser(tokenId: UUID): User
}

interface ILoginRegisterRepository {
    suspend fun login(
        loginPassword: AuthenticationForm,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Token?

    suspend fun register(account: RegistrationForm): Boolean
}

interface IChatRepository {
    suspend fun getChats(user: User): List<IChat>

    suspend fun getMembers(
        user: User,
        chat: IChat,
    ): List<User>

    suspend fun createChat(users: List<User>): IChat?

    suspend fun getChat(
        user: User,
        id: UUID,
    ): IChat?

    suspend fun existInChat(
        user: User,
        chat: IChat,
    ): Boolean
}

interface IMessageRepository {
    suspend fun getMessages(
        user: User,
        chat: IChat,
        limit: Int = 64,
        startFrom: Long? = null,
    ): List<Message>

    suspend fun sendMessage(
        user: User,
        chat: IChat,
        message: MessageForm,
    ): Message
}
