package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.model.*
import kotlinx.coroutines.flow.Flow

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
    suspend fun createAPI(token: String)

    suspend fun closeAPI(token: String)

    suspend fun getChats(token: String): List<IChat>

    suspend fun filterUsers(
        token: String,
        query: String,
    ): List<User>

    suspend fun getChat(
        token: String,
        chatModel: IChat,
    ): IChatWebSocketModel

    suspend fun getMyOwnAccount(token: String): User

    suspend fun createChat(
        token: String,
        user: User,
    )

    suspend fun getNotifications(token: String): INotificationWebSocketModel

    suspend fun getMessages(
        token: String,
        chatModel: IChat,
        key: MessagePageKey?,
    ): MessagePage

    suspend fun editProfile(
        token: String,
        profileForm: ProfileForm,
    )

    suspend fun uploadProfilePhoto(
        token: String,
        type: String,
        bytes: ByteArray,
    ): Image

    suspend fun getImageContent(
        token: String,
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
    suspend fun register(registrationForm: RegistrationForm): Boolean

    suspend fun login(loginPasswordModel: AuthenticationForm): String?
}
