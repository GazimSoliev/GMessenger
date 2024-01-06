package com.gazim.gmessenger.domain.model

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

sealed interface IUserModel {
    val nickname: String
    val username: String
}

sealed interface ILoginModel {
    val login: String
}

sealed interface IPasswordModel {
    val password: String
}

sealed interface ILoginPasswordModel : ILoginModel, IPasswordModel

sealed interface IAccountModel : IUserModel, ILoginPasswordModel

sealed interface ISentMessageModel {
    val message: String
}

sealed interface IMessageModel : ISentMessageModel {
    val sentAt: LocalDateTime
    val user: IUserModel
}

sealed interface INotificationModel

sealed interface INotificationMessageModel : IMessageModel, INotificationModel {
    val chatName: String
}

sealed interface IYourMessageModel : IMessageModel

sealed interface IChatModel {
    val identifier: String
    val title: String
}

sealed interface IPrivateChatModel : IChatModel {
    val user: IUserModel
}

interface IChatWebSocketModel {
    val chatName: String
    val messages: Flow<List<IMessageModel>>

    suspend fun sendMessage(msg: ISentMessageModel)

    suspend fun openConnection()

    fun close()
}

interface INotificationWebSocketModel {
    val notifications: Flow<INotificationModel>

    suspend fun openConnection()

    suspend fun closeConnection()
}
