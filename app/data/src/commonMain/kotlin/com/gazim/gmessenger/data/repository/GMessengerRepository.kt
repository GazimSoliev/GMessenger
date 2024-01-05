package com.gazim.gmessenger.data.repository

import com.gazim.api.gmessenger.model.IChat
import com.gazim.api.gmessenger.model.IUser
import com.gazim.api.gmessenger.repository.GMessengerAPI
import com.gazim.api.gmessenger.repository.IGMessengerAPI
import com.gazim.gmessenger.client.pc.data.model.*
import com.gazim.gmessenger.client.pc.domain.model.*
import com.gazim.gmessenger.client.pc.domain.repository.IGMessengerRepository
import com.gazim.gmessenger.data.model.toChatModel
import com.gazim.gmessenger.data.model.toUserModel

class GMessengerRepository(token: String) : IGMessengerRepository {
    private val gMessengerAPI: IGMessengerAPI = GMessengerAPI(token)

    override suspend fun getChats(): List<IChatModel> = gMessengerAPI.getChats().map(IChat::toChatModel)

    override suspend fun filterUsers(query: String): List<IUserModel> = gMessengerAPI.findUser(query).map(IUser::toUserModel)

    override suspend fun getChat(chatModel: IChatModel): IChatWebSocketModel =
        gMessengerAPI.getChatWebSocket(chatModel.toChat())
            .toChatWebSocketModel((if (chatModel is IPrivateChatModel) chatModel.user.nickname else chatModel.title))

    override suspend fun getMyOwnAccount(): IUserModel = gMessengerAPI.whoAmI().toUserModel()

    override suspend fun createChat(user: IUserModel) {
        gMessengerAPI.createChat(user = user.toUser())
    }

    override suspend fun getNotifications(): INotificationWebSocketModel = gMessengerAPI.getNotifications().toNotificationWebSocketModel()
}
