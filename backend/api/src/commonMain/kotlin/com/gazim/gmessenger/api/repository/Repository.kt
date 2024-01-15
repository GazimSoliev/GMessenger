package com.gazim.gmessenger.api.repository

import com.gazim.gmessenger.backend.common.model.*
import kotlinx.coroutines.flow.Flow
import java.io.Closeable

interface IGMessengerAPI {
    suspend fun whoAmI(): IUserPresent

    suspend fun getChats(): List<IChatPresent>

    suspend fun createChat(user: IUserPresent): Boolean

    fun getChatWebSocket(chat: IChatPresent): IChatWebSocket

    suspend fun findUser(username: String): List<IUserPresent>

    suspend fun getNotifications(): INotificationSocket
}

interface IGMessengerAuthAPI {
    suspend fun register(account: IAccountPresent): Boolean

    suspend fun login(loginPassword: ILoginPasswordPresent): String?
}

interface IChatWebSocket : Closeable {
    val messages: Flow<List<IMessagePresent>>

    suspend fun openConnection()

    suspend fun sendMessage(message: ISentMessagePresent)
}

interface INotificationSocket {
    val notifications: Flow<INotificationPresent>

    suspend fun openConnection()

    suspend fun closeConnection()
}
