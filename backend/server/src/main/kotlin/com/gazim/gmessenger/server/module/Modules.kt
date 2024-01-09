package com.gazim.gmessenger.server.module

import com.gazim.gmessenger.backend.common.model.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface IGMessengerModule

interface IRegistrationModule : IGMessengerModule {
    suspend fun register(account: IAccount): Boolean
}

interface ILoginModule : IGMessengerModule {
    suspend fun login(loginPassword: ILoginPassword): Int?
}

interface IChatModule : IGMessengerModule {
    suspend fun getChats(user: IUser): List<IChat>

    suspend fun createChat(users: List<IUser>): Boolean

    suspend fun getChat(
        user: IUser,
        chatId: Int,
    ): IChat?

    suspend fun getChatMembers(
        user: IUser,
        chat: IChat,
    ): List<IUser>
}

interface IMessageModule : IGMessengerModule {
    suspend fun getMessages(
        chat: IChat,
        page: Int,
    ): Page<IMessage>

    suspend fun sendMessage(
        chat: IChat,
        message: IMessage,
    ): Boolean
}

interface IUserModule : IGMessengerModule {
    suspend fun getUser(idToken: Int): IUser

    suspend fun findUser(username: String): List<IUser>
}

interface INotificationModule : IGMessengerModule {
    suspend fun getNotifications(user: IUser): Flow<INotification>

    suspend fun sendNotification(
        user: IUser,
        notification: INotification,
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
