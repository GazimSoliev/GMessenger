package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.model.IChat
import com.gazim.gmessenger.domain.model.MessagePageKey
import com.gazim.gmessenger.domain.model.ProfileForm
import com.gazim.gmessenger.domain.model.User

class GMessengerSessionServiceImpl(
    private val sessionService: SessionService,
    private val gMessengerService: GMessengerService,
) : GMessengerSessionService {
    private var token = ""

    override suspend fun createCurrentSession() {
        token = sessionService.currentToken()!!
        gMessengerService.createAPI(token)
    }

    override suspend fun closeCurrentSession() {
        gMessengerService.closeAPI(token)
        token = ""
    }

    override suspend fun getChats() = gMessengerService.getChats(token)

    override suspend fun filterUsers(query: String) = gMessengerService.filterUsers(token, query)

    override suspend fun getChat(chatModel: IChat) = gMessengerService.getChat(token, chatModel)

    override suspend fun getMyOwnAccount() = gMessengerService.getMyOwnAccount(token)

    override suspend fun createChat(user: User) = gMessengerService.createChat(token, user)

    override suspend fun getNotifications() = gMessengerService.getNotifications(token)

    override suspend fun getMessages(
        chatModel: IChat,
        key: MessagePageKey?,
    ) = gMessengerService.getMessages(token, chatModel, key)

    override suspend fun editProfile(profileForm: ProfileForm) = gMessengerService.editProfile(token, profileForm)

    override suspend fun uploadProfilePhoto(
        type: String,
        bytes: ByteArray,
    ) = gMessengerService.uploadProfilePhoto(token, type, bytes)

    override suspend fun getImageContent(photoId: String) = gMessengerService.getImageContent(token, photoId)
}
