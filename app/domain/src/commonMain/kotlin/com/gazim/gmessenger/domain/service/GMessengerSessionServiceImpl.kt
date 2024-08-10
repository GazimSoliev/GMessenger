package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.model.*

class GMessengerSessionServiceImpl(
    private val sessionService: SessionService,
    private val gMessengerService: GMessengerService,
    private val gMessengerConnectionService: GMessengerConnectionService,
) : GMessengerSessionService {
    private var _config: APIConfig? = null
    private val config get() = _config ?: error("API config not set")

    override suspend fun createCurrentSession() {
        val token = sessionService.currentToken()!!
        val server = gMessengerConnectionService.getCurrentServer()!!
        val config = APIConfig(server.host, server.isSecure, token)
        gMessengerService.createAPI(config)
        _config = config
    }

    override suspend fun closeCurrentSession() {
        gMessengerService.closeAPI(config)
        _config = null
    }

    override suspend fun getChats() = gMessengerService.getChats(config)

    override suspend fun filterUsers(query: String) = gMessengerService.filterUsers(config, query)

    override suspend fun getChat(chatModel: IChat) = gMessengerService.getChat(config, chatModel)

    override suspend fun getMyOwnAccount() = gMessengerService.getMyOwnAccount(config)

    override suspend fun createChat(user: User) = gMessengerService.createChat(config, user)

    override suspend fun getNotifications() = gMessengerService.getNotifications(config)

    override suspend fun getMessages(
        chatModel: IChat,
        key: MessagePageKey?,
    ) = gMessengerService.getMessages(config, chatModel, key)

    override suspend fun editProfile(profileForm: ProfileForm) = gMessengerService.editProfile(config, profileForm)

    override suspend fun uploadProfilePhoto(
        type: String,
        bytes: ByteArray,
    ) = gMessengerService.uploadProfilePhoto(config, type, bytes)

    override suspend fun getImageContent(photoId: String) = gMessengerService.getImageContent(config, photoId)
}
