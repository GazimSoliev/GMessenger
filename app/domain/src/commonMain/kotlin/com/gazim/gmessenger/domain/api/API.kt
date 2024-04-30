package com.gazim.gmessenger.domain.api

import com.gazim.gmessenger.domain.model.*
import kotlin.time.Duration

interface GMessengerAuthAPI {
    suspend fun register(registrationForm: RegistrationForm): Boolean

    suspend fun login(loginPasswordModel: AuthenticationForm): String?
}

interface GMessengerAPI {
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

    suspend fun getImageContent(photoId: String): ByteArray
}

interface GMessengerConnectionAPI {
    val availableServers: List<GMessengerServer>

    suspend fun ping(url: String): Duration
}
