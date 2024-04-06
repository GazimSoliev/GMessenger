package com.gazim.gmessenger.data.service

import com.gazim.gmessenger.api.GMessengerAPI
import com.gazim.gmessenger.api.IGMessengerAPI
import com.gazim.gmessenger.api.model.IChat
import com.gazim.gmessenger.api.model.User
import com.gazim.gmessenger.data.model.toAPI
import com.gazim.gmessenger.data.model.toChatWebSocketModel
import com.gazim.gmessenger.data.model.toDomain
import com.gazim.gmessenger.data.model.toNotificationWebSocketModel
import com.gazim.gmessenger.domain.model.*
import com.gazim.gmessenger.domain.service.IGMessengerService

class GMessengerService(token: String) : IGMessengerService {
    private val gMessengerAPI: IGMessengerAPI = GMessengerAPI(token)

    override suspend fun getChats(): List<IChatModel> = gMessengerAPI.getChats().map(IChat::toDomain)

    override suspend fun filterUsers(query: String): List<IUserModel> =
        gMessengerAPI.findUser(query).map(User::toDomain)

    override suspend fun getChat(chatModel: IChatModel): IChatWebSocketModel =
        gMessengerAPI.getChatWebSocket(chatModel.toAPI())
            .toChatWebSocketModel((if (chatModel is IPrivateChatModel) chatModel.user.nickname else chatModel.title))

    override suspend fun getMyOwnAccount(): IUserModel = gMessengerAPI.whoAmI().toDomain()

    override suspend fun createChat(user: IUserModel) {
        gMessengerAPI.createChat(user = user.toAPI())
    }

    override suspend fun getNotifications(): INotificationWebSocketModel =
        gMessengerAPI.getNotifications().toNotificationWebSocketModel()

    override suspend fun getMessages(
        chatModel: IChatModel,
        key: MessagePageKey?,
    ): MessagePage = gMessengerAPI.getMessages(chatModel.toAPI(), key?.toAPI()).toDomain()

    override suspend fun editProfile(profileForm: ProfileForm) =
        gMessengerAPI.editProfile(profileForm.toAPI())

}
