@file:Suppress("SpellCheckingInspection")

package com.gazim.gmessenger.data.api

import com.gazim.gmessenger.data.model.toAPI
import com.gazim.gmessenger.data.model.toChatWebSocketModel
import com.gazim.gmessenger.data.model.toDomain
import com.gazim.gmessenger.data.model.toNotificationWebSocketModel
import com.gazim.gmessenger.domain.api.GMessengerAPI
import com.gazim.gmessenger.domain.model.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import com.gazim.gmessenger.core.GMessengerAPI as GMAPI
import com.gazim.gmessenger.core.model.IChat as IChatAPI
import com.gazim.gmessenger.core.model.User as UserAPI

@OptIn(ExperimentalUuidApi::class)
class GMessengerAPIImpl(
    host: String,
    isSecure: Boolean,
    token: String,
) : GMessengerAPI {
    private val gMessengerAPI = GMAPI(host = host, isSecure = isSecure, token = token)

    override suspend fun getChats(): List<IChat> = gMessengerAPI.getChats().map(IChatAPI::toDomain)

    override suspend fun filterUsers(query: String): List<User> = gMessengerAPI.findUser(query).map(UserAPI::toDomain)

    override suspend fun getChat(chatUi: Uuid): IChatWebSocketModel =
        gMessengerAPI
            .getChatWebSocket(chatUi)
            .toChatWebSocketModel()

    override suspend fun getMyOwnAccount(): User = gMessengerAPI.whoAmI().toDomain()

    override suspend fun createChat(userId: Uuid) {
        gMessengerAPI.createChat(userId)
    }

    override suspend fun getNotifications(): INotificationWebSocketModel = gMessengerAPI.getNotifications().toNotificationWebSocketModel()

    override suspend fun getMessages(
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage = gMessengerAPI.getMessages(chatId, key?.toAPI()).toDomain()

    override suspend fun editProfile(profileForm: ProfileForm) = gMessengerAPI.editProfile(profileForm.toAPI())

    override suspend fun uploadProfilePhoto(
        type: String,
        bytes: ByteArray,
    ): Image = gMessengerAPI.uploadProfilePhoto(type, bytes).toDomain()

    override suspend fun getImageContent(photoId: Uuid): ByteArray = gMessengerAPI.getImageContent(photoId)

    override fun close() {
        gMessengerAPI.close()
    }
}
