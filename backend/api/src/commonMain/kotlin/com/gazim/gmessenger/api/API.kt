@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.api

import com.gazim.gmessenger.api.model.*
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface GMessengerAPI : AutoCloseable {
    suspend fun whoAmI(): User

    suspend fun getChats(): List<IChat>

    suspend fun getChat(chatId: Uuid): IChat

    suspend fun createChat(userID: Uuid): Boolean

    fun getChatWebSocket(chatID: Uuid): ChatWebSocket

    suspend fun findUser(username: String): List<User>

    suspend fun getNotifications(): NotificationSocket

    suspend fun getMessages(
        chatId: Uuid,
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

    suspend fun login(loginPassword: AuthenticationForm): Token
}

interface GMessengerAPIConnection {
    val availableServers: List<GMessengerServer>

    suspend fun ping(
        host: String,
        isSecure: Boolean,
    ): Duration
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
