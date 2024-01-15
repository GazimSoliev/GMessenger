package com.gazim.gmessenger.server.module

import com.gazim.gmessenger.backend.common.model.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface IGMessengerModule

interface IRegistrationModule : IGMessengerModule {
    suspend fun register(account: IAccountPresent): Boolean
}

interface ILoginModule : IGMessengerModule {
    suspend fun login(loginPassword: ILoginPasswordPresent): Int?
}

interface IChatModule : IGMessengerModule {
    suspend fun getChats(user: IUserPresent): List<IChatPresent>

    suspend fun createChat(users: List<IUserPresent>): Boolean

    suspend fun getChat(
        user: IUserPresent,
        chatId: Int,
    ): IChatPresent?

    suspend fun getChatMembers(
        user: IUserPresent,
        chat: IChatPresent,
    ): List<IUserPresent>
}

interface IMessageModule : IGMessengerModule {
    suspend fun getMessages(
        chat: IChatPresent,
        page: Int,
    ): PagePresent<IMessagePresent>

    suspend fun sendMessage(
        chat: IChatPresent,
        message: IMessagePresent,
    ): Boolean
}

interface IUserModule : IGMessengerModule {
    suspend fun getUser(idToken: Int): IUserPresent

    suspend fun findUser(username: String): List<IUserPresent>
}

interface INotificationModule : IGMessengerModule {
    suspend fun getNotifications(user: IUserPresent): Flow<INotificationPresent>

    suspend fun sendNotification(
        user: IUserPresent,
        notification: INotificationPresent,
    )
}

interface ITokenModule {
    suspend fun generateJWT(
        tokenId: Int,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): String

    suspend fun registerToken(
        accountId: Int,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Int?

    suspend fun registerAndGenerateToken(
        accountId: Int,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): String?

    suspend fun registerAndGenerateToken(accountId: Int): String?
}
