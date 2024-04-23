package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.api.GMessengerAPI
import com.gazim.gmessenger.domain.model.*

class GMessengerServiceImpl(private val gMessengerAPI: GMessengerAPI) : GMessengerService {
    override suspend fun getChats(): List<IChat> = gMessengerAPI.getChats()

    override suspend fun filterUsers(query: String): List<User> = gMessengerAPI.filterUsers(query)

    override suspend fun getChat(chatModel: IChat): IChatWebSocketModel = gMessengerAPI.getChat(chatModel)

    override suspend fun getMyOwnAccount(): User = gMessengerAPI.getMyOwnAccount()

    override suspend fun createChat(user: User) = gMessengerAPI.createChat(user)

    override suspend fun getNotifications(): INotificationWebSocketModel = gMessengerAPI.getNotifications()

    override suspend fun getMessages(
        chatModel: IChat,
        key: MessagePageKey?,
    ): MessagePage = gMessengerAPI.getMessages(chatModel, key)

    override suspend fun editProfile(profileForm: ProfileForm) = gMessengerAPI.editProfile(profileForm)

    override suspend fun uploadProfilePhoto(
        type: String,
        bytes: ByteArray,
    ): Image = gMessengerAPI.uploadProfilePhoto(type, bytes)

    override suspend fun getImageContent(photoId: String): ByteArray = gMessengerAPI.getImageContent(photoId)
}
