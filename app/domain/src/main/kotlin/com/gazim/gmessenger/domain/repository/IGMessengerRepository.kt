package com.gazim.gmessenger.domain.repository

import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.model.IChatWebSocketModel
import com.gazim.gmessenger.domain.model.INotificationWebSocketModel
import com.gazim.gmessenger.domain.model.IUserModel

interface IGMessengerRepository {
    suspend fun getChats(): List<IChatModel>

    suspend fun filterUsers(query: String): List<IUserModel>

    suspend fun getChat(chatModel: IChatModel): IChatWebSocketModel

    suspend fun getMyOwnAccount(): IUserModel

    suspend fun createChat(user: IUserModel)

    suspend fun getNotifications(): INotificationWebSocketModel
}
