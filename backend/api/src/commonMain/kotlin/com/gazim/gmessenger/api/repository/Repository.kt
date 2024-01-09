package com.gazim.gmessenger.api.repository

import com.gazim.gmessenger.backend.common.model.*
import kotlinx.coroutines.flow.Flow
import java.io.Closeable

interface IGMessengerAPI {
    suspend fun whoAmI(): IUser

    suspend fun getChats(): List<IChat>

    suspend fun createChat(user: IUser): Boolean

    fun getChatWebSocket(chat: IChat): IChatWebSocket

    suspend fun findUser(username: String): List<IUser>

    suspend fun getNotifications(): INotificationSocket
}

interface IGMessengerAuthAPI {
    suspend fun register(account: IAccount): Boolean

    suspend fun login(loginPassword: ILoginPassword): String?
}

interface IChatWebSocket : Closeable {
    val messages: Flow<List<IMessage>>

    suspend fun openConnection()

    suspend fun sendMessage(message: ISentMessage)
}

interface INotificationSocket {
    val notifications: Flow<INotification>

    suspend fun openConnection()

    suspend fun closeConnection()
}
