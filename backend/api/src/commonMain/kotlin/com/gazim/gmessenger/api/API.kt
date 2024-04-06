package com.gazim.gmessenger.api

import com.gazim.gmessenger.api.model.*
import kotlinx.coroutines.flow.Flow
import java.io.Closeable

interface IGMessengerAPI {
    suspend fun whoAmI(): User

    suspend fun getChats(): List<IChat>

    suspend fun createChat(user: User): Boolean

    fun getChatWebSocket(chat: IChat): IChatWebSocket

    suspend fun findUser(username: String): List<User>

    suspend fun getNotifications(): INotificationSocket

    suspend fun getMessages(
        chat: IChat,
        key: MessagePageKey?,
    ): MyMessagePage

    suspend fun editProfile(profileForm: ProfileForm)
}

interface IGMessengerAuthAPI {
    suspend fun register(account: RegistrationForm): Boolean

    suspend fun login(loginPassword: AuthenticationForm): String?
}

interface IChatWebSocket : Closeable {
    val messages: Flow<IMessage>

    suspend fun openConnection()

    suspend fun sendMessage(message: MessageForm)
}

interface INotificationSocket {
    val notifications: Flow<MessageNotification>

    suspend fun openConnection()

    suspend fun closeConnection()
}
