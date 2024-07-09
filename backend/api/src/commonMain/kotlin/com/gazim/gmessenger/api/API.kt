package com.gazim.gmessenger.api

import com.gazim.gmessenger.api.model.*
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface GMessengerAPI : AutoCloseable {
    suspend fun whoAmI(): User

    suspend fun getChats(): List<IChat>

    suspend fun createChat(user: User): Boolean

    fun getChatWebSocket(chat: IChat): ChatWebSocket

    suspend fun findUser(username: String): List<User>

    suspend fun getNotifications(): ChatWebSocket.NotificationSocket

    suspend fun getMessages(
        chat: IChat,
        key: MessagePageKey?,
    ): MyMessagePage

    suspend fun editProfile(profileForm: ProfileForm)

    suspend fun uploadProfilePhoto(
        type: String,
        bytes: ByteArray,
    ): Image

    suspend fun getImageContent(photoId: String): ByteArray
}

interface GMessengerAuthAPI {
    suspend fun register(account: RegistrationForm): Boolean

    suspend fun login(loginPassword: AuthenticationForm): String?
}

interface ChatWebSocket : AutoCloseable {
interface GMessengerAPIConnection {
    val availableServers: List<GMessengerServer>

    suspend fun ping(url: String): Duration
}

interface ChatWebSocket : AutoCloseable {
    val messages: Flow<IMessage>

    suspend fun openConnection()

    suspend fun sendMessage(message: MessageForm)
}

interface NotificationSocket {
    val notifications: Flow<MessageNotification>

    suspend fun openConnection()

    suspend fun closeConnection()
}
