@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface SessionService {
    fun currentToken(): String?

    fun currentSession(): String?

    fun setSession(token: String)

    fun clearSession()
}

interface NotificationService {
    val notifications: Flow<Notification>

    suspend fun openConnection()

    suspend fun closeConnection()
}

interface GMessengerService {
    suspend fun createAPI(config: APIConfig)

    suspend fun closeAPI(config: APIConfig)

    suspend fun getChats(config: APIConfig): List<IChat>

    suspend fun filterUsers(
        config: APIConfig,
        query: String,
    ): List<User>

    suspend fun getChat(
        config: APIConfig,
        chatUi: Uuid,
    ): IChatWebSocketModel

    suspend fun getMyOwnAccount(config: APIConfig): User

    suspend fun createChat(
        config: APIConfig,
        userId: Uuid,
    )

    suspend fun getNotifications(config: APIConfig): INotificationWebSocketModel

    suspend fun getMessages(
        config: APIConfig,
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage

    suspend fun editProfile(
        config: APIConfig,
        profileForm: ProfileForm,
    )

    suspend fun uploadProfilePhoto(
        config: APIConfig,
        type: String,
        bytes: ByteArray,
    ): Image

    suspend fun getImageContent(
        config: APIConfig,
        photoId: Uuid,
    ): ByteArray
}

interface GMessengerSessionService {
    suspend fun createCurrentSession()

    suspend fun closeCurrentSession()

    suspend fun getChats(): List<IChat>

    suspend fun filterUsers(query: String): List<User>

    suspend fun getChat(chatUi: Uuid): IChatWebSocketModel

    suspend fun getMyOwnAccount(): User

    suspend fun createChat(userId: Uuid)

    suspend fun getNotifications(): INotificationWebSocketModel

    suspend fun getMessages(
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage

    suspend fun editProfile(profileForm: ProfileForm)

    suspend fun uploadProfilePhoto(
        type: String,
        bytes: ByteArray,
    ): Image

    suspend fun getImageContent(photoId: Uuid): ByteArray
}

interface GMessengerAuthService {
    suspend fun register(
        config: AuthAPIConfig,
        registrationForm: RegistrationForm,
    ): Boolean

    suspend fun login(
        config: AuthAPIConfig,
        loginPasswordModel: AuthenticationForm,
    ): String?

    suspend fun createAPI(config: AuthAPIConfig)

    suspend fun closeAPI(config: AuthAPIConfig)
}

interface GMessengerAuthSessionService {
    suspend fun register(registrationForm: RegistrationForm): Boolean

    suspend fun login(loginPasswordModel: AuthenticationForm): String?

    suspend fun createAPI()

    suspend fun closeAPI()
}

interface GMessengerConnectionService {
    val availableServers: List<GMessengerServer>

    suspend fun ping(
        host: String,
        isSecure: Boolean,
    ): Duration

    suspend fun addServer(server: GMessengerServer)

    fun getCurrentServer(): GMessengerServer?

    suspend fun applyServer(server: GMessengerServer)
}
