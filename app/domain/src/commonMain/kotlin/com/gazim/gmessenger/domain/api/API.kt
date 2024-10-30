@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.domain.api

import com.gazim.gmessenger.domain.model.*
import kotlin.time.Duration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface GMessengerAuthAPI {
    suspend fun register(registrationForm: RegistrationForm): Boolean

    suspend fun login(loginPasswordModel: AuthenticationForm): String?
}

interface GMessengerAPI : AutoCloseable {
    suspend fun getChats(): List<IChat>

    suspend fun filterUsers(query: String): List<User>

    suspend fun getChat(chatModel: IChat): IChatWebSocketModel

    suspend fun getMyOwnAccount(): User

    suspend fun createChat(user: User)

    suspend fun getNotifications(): INotificationWebSocketModel

    suspend fun getMessages(
        chatModel: IChat,
        key: MessagePageKey?,
    ): MessagePage

    suspend fun editProfile(profileForm: ProfileForm)

    suspend fun uploadProfilePhoto(
        type: String,
        bytes: ByteArray,
    ): Image

    suspend fun getImageContent(photoId: Uuid): ByteArray
}

interface GMessengerConnectionAPI {
    val availableServers: List<GMessengerServer>

    suspend fun ping(
        host: String,
        isSecure: Boolean,
    ): Duration
}
