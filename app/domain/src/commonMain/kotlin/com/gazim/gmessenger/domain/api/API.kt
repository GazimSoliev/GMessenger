package com.gazim.gmessenger.domain.api

import com.gazim.gmessenger.domain.model.*

interface GMessengerAuthAPI {
    suspend fun register(accountModel: AccountModel): Boolean

    suspend fun login(loginPasswordModel: ILoginPasswordModel): String
}

interface GMessengerAPI {
    suspend fun getChats(): List<IChatModel>

    suspend fun filterUsers(query: String): List<IUserModel>

    suspend fun getChat(chatModel: IChatModel): IChatWebSocketModel

    suspend fun getMyOwnAccount(): IUserModel

    suspend fun createChat(user: IUserModel)

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
