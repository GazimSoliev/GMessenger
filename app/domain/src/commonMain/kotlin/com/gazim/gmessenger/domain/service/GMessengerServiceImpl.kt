package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.api.GMessengerAPI
import com.gazim.gmessenger.domain.factrory.GMessengerAPIFactory
import com.gazim.gmessenger.domain.model.APIConfig
import com.gazim.gmessenger.domain.model.MessagePageKey
import com.gazim.gmessenger.domain.model.ProfileForm
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@ExperimentalUuidApi
class GMessengerServiceImpl(
    private val gMessengerAPIFactory: GMessengerAPIFactory,
) : GMessengerService {
    private val mapApi = mutableMapOf<APIConfig, GMessengerAPI>()

    override suspend fun createAPI(config: APIConfig) {
        mapApi[config] = gMessengerAPIFactory(config)
    }

    override suspend fun closeAPI(config: APIConfig) {
        val api = mapApi.remove(config)
        checkNotNull(api)
        api.close()
    }

    override suspend fun getChats(config: APIConfig) = getAPI(config).getChats()

    override suspend fun filterUsers(
        config: APIConfig,
        query: String,
    ) = getAPI(config).filterUsers(query)

    override suspend fun getChat(
        config: APIConfig,
        chatUi: Uuid,
    ) = getAPI(config).getChat(chatUi)

    override suspend fun getMyOwnAccount(config: APIConfig) = getAPI(config).getMyOwnAccount()

    override suspend fun createChat(
        config: APIConfig,
        userId: Uuid,
    ) = getAPI(config).createChat(userId)

    override suspend fun getNotifications(config: APIConfig) = getAPI(config).getNotifications()

    override suspend fun getMessages(
        config: APIConfig,
        chatId: Uuid,
        key: MessagePageKey?,
    ) = getAPI(config).getMessages(chatId, key)

    override suspend fun editProfile(
        config: APIConfig,
        profileForm: ProfileForm,
    ) = getAPI(config).editProfile(profileForm)

    override suspend fun uploadProfilePhoto(
        config: APIConfig,
        type: String,
        bytes: ByteArray,
    ) = getAPI(config).uploadProfilePhoto(type, bytes)

    override suspend fun getImageContent(
        config: APIConfig,
        photoId: Uuid,
    ): ByteArray = getAPI(config).getImageContent(photoId)

    private fun getAPI(config: APIConfig) = mapApi.getValue(config)
}
