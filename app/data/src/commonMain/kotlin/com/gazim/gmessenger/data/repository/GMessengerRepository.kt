package com.gazim.gmessenger.data.repository

import com.gazim.gmessenger.api.repository.GMessengerAPI
import com.gazim.gmessenger.api.repository.IGMessengerAPI
import com.gazim.gmessenger.backend.common.model.IChatPresent
import com.gazim.gmessenger.backend.common.model.IUserPresent
import com.gazim.gmessenger.data.model.*
import com.gazim.gmessenger.domain.model.*
import com.gazim.gmessenger.domain.repository.IGMessengerRepository

class GMessengerRepository(token: String) : IGMessengerRepository {
    private val gMessengerAPI: IGMessengerAPI = GMessengerAPI(token)

    override suspend fun getChats(): List<IChatModel> = gMessengerAPI.getChats().map(IChatPresent::toChatModel)

    override suspend fun filterUsers(query: String): List<IUserModel> = gMessengerAPI.findUser(query).map(IUserPresent::toUserModel)

    override suspend fun getChat(chatModel: IChatModel): IChatWebSocketModel =
        gMessengerAPI.getChatWebSocket(chatModel.toChat())
            .toChatWebSocketModel((if (chatModel is IPrivateChatModel) chatModel.user.nickname else chatModel.title))

    override suspend fun getMyOwnAccount(): IUserModel = gMessengerAPI.whoAmI().toUserModel()

    override suspend fun createChat(user: IUserModel) {
        gMessengerAPI.createChat(user = user.toUser())
    }

    override suspend fun getNotifications(): INotificationWebSocketModel = gMessengerAPI.getNotifications().toNotificationWebSocketModel()
}
