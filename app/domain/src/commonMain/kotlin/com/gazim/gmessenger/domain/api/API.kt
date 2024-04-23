package com.gazim.gmessenger.domain.api

import com.gazim.gmessenger.domain.model.*

interface GMessengerAuthAPI {
    suspend fun register(registrationForm: RegistrationForm): Boolean

    suspend fun login(loginPasswordModel: AuthenticationForm): String
}

interface GMessengerAPI {
    suspend fun getChats(): List<IChatModel>

    suspend fun filterUsers(query: String): List<User>

    suspend fun getChat(chatModel: IChatModel): IChatWebSocketModel

    suspend fun getMyOwnAccount(): User

    suspend fun createChat(user: User)

    suspend fun getNotifications(): INotificationWebSocketModel

    suspend fun getMessages(
        chatModel: IChatModel,
        key: MessagePageKey?,
    ): MessagePage

    suspend fun editProfile(profileForm: ProfileForm)

    suspend fun uploadProfilePhoto(
        type: String,
        bytes: ByteArray,
    ): Image

    suspend fun getImageContent(photoId: String): ByteArray
}
