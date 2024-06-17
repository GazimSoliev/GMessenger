package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.api.GMessengerAPI
import com.gazim.gmessenger.domain.factrory.GMessengerAPIFactory
import com.gazim.gmessenger.domain.model.*

class GMessengerServiceImpl(
    private val gMessengerAPIFactory: GMessengerAPIFactory,
) : GMessengerService {
    private val mapApi = mutableMapOf<String, GMessengerAPI>()

    override suspend fun createAPI(token: String) {
        mapApi[token] = gMessengerAPIFactory(token)
    }

    override suspend fun closeAPI(token: String) {
        checkNotNull(mapApi.remove(token))
    }

    override suspend fun getChats(token: String): List<IChat> = mapApi.getValue(token).getChats()

    override suspend fun filterUsers(
        token: String,
        query: String,
    ): List<User> = mapApi.getValue(token).filterUsers(query)

    override suspend fun getChat(
        token: String,
        chatModel: IChat,
    ): IChatWebSocketModel = mapApi.getValue(token).getChat(chatModel)

    override suspend fun getMyOwnAccount(token: String): User = mapApi.getValue(token).getMyOwnAccount()

    override suspend fun createChat(
        token: String,
        user: User,
    ) = mapApi.getValue(token).createChat(user)

    override suspend fun getNotifications(token: String): INotificationWebSocketModel = mapApi.getValue(token).getNotifications()

    override suspend fun getMessages(
        token: String,
        chatModel: IChat,
        key: MessagePageKey?,
    ): MessagePage = mapApi.getValue(token).getMessages(chatModel, key)

    override suspend fun editProfile(
        token: String,
        profileForm: ProfileForm,
    ) = mapApi.getValue(token).editProfile(profileForm)

    override suspend fun uploadProfilePhoto(
        token: String,
        type: String,
        bytes: ByteArray,
    ): Image = mapApi.getValue(token).uploadProfilePhoto(type, bytes)

    override suspend fun getImageContent(
        token: String,
        photoId: String,
    ): ByteArray = mapApi.getValue(token).getImageContent(photoId)
}
