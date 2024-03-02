package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.model.*
import kotlinx.coroutines.flow.Flow

interface ISessionService {
    fun currentToken(): String?

    fun currentSession(): String?

    fun setSession(token: String)

    fun clearSession()
}

interface INotificationService {
    val notifications: Flow<INotificationModel>

    suspend fun openConnection()

    suspend fun closeConnection()
}

interface IGMessengerService {
    suspend fun getChats(): List<IChatModel>

    suspend fun filterUsers(query: String): List<IUserModel>

    suspend fun getChat(chatModel: IChatModel): IChatWebSocketModel

    suspend fun getMyOwnAccount(): IUserModel

    suspend fun createChat(user: IUserModel)

    suspend fun getNotifications(): INotificationWebSocketModel
}

interface IGMessengerAuthService {
    suspend fun register(accountModel: IAccountModel): Boolean

    suspend fun login(loginPasswordModel: ILoginPasswordModel): String
}
