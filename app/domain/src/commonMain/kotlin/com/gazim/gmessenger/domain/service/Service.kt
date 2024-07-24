package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

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
        chatModel: IChat,
    ): IChatWebSocketModel

    suspend fun getMyOwnAccount(config: APIConfig): User

    suspend fun createChat(
        config: APIConfig,
        user: User,
    )

    suspend fun getNotifications(config: APIConfig): INotificationWebSocketModel

    suspend fun getMessages(
        config: APIConfig,
        chatModel: IChat,
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
        photoId: String,
    ): ByteArray
}

interface GMessengerSessionService {
    suspend fun createCurrentSession()

    suspend fun closeCurrentSession()

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
