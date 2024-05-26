@file:Suppress("SpellCheckingInspection")

package com.gazim.gmessenger.data.api

import com.gazim.gmessenger.api.model.IChat
import com.gazim.gmessenger.data.model.toAPI
import com.gazim.gmessenger.data.model.toChatWebSocketModel
import com.gazim.gmessenger.data.model.toDomain
import com.gazim.gmessenger.data.model.toNotificationWebSocketModel
import com.gazim.gmessenger.domain.api.GMessengerAPI
import com.gazim.gmessenger.domain.model.*
import com.gazim.gmessenger.api.GMessengerAPI as GMAPI
import com.gazim.gmessenger.api.model.User as UserAPI

class GMessengerAPIImpl(host: String, token: String) : GMessengerAPI {
    private val gMessengerAPI = GMAPI(host, token)

    override suspend fun getChats(): List<com.gazim.gmessenger.domain.model.IChat> = gMessengerAPI.getChats().map(IChat::toDomain)

    override suspend fun filterUsers(query: String): List<User> = gMessengerAPI.findUser(query).map(UserAPI::toDomain)

    override suspend fun getChat(chatModel: com.gazim.gmessenger.domain.model.IChat): IChatWebSocketModel =
        gMessengerAPI.getChatWebSocket(chatModel.toAPI())
            .toChatWebSocketModel((if (chatModel is PrivateChat) chatModel.user.nickname else chatModel.title))

    override suspend fun getMyOwnAccount(): User = gMessengerAPI.whoAmI().toDomain()

    override suspend fun createChat(user: User) {
        gMessengerAPI.createChat(user = user.toAPI())
    }

    override suspend fun getNotifications(): INotificationWebSocketModel = gMessengerAPI.getNotifications().toNotificationWebSocketModel()

    override suspend fun getMessages(
        chatModel: com.gazim.gmessenger.domain.model.IChat,
        key: MessagePageKey?,
    ): MessagePage = gMessengerAPI.getMessages(chatModel.toAPI(), key?.toAPI()).toDomain()

    override suspend fun editProfile(profileForm: ProfileForm) = gMessengerAPI.editProfile(profileForm.toAPI())

    override suspend fun uploadProfilePhoto(
        type: String,
        bytes: ByteArray,
    ): Image = gMessengerAPI.uploadProfilePhoto(type, bytes).toDomain()

    override suspend fun getImageContent(photoId: String): ByteArray = gMessengerAPI.getImageContent(photoId)
}
