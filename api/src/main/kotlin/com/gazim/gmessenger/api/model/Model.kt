package com.gazim.gmessenger.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
sealed interface IUser {
    val nickname: String
    val username: String
}

@Serializable
sealed interface ILogin {
    val login: String
}

@Serializable
sealed interface IPassword {
    val password: String
}

@Serializable
sealed interface ILoginPassword : ILogin, IPassword

@Serializable
sealed interface IAccount : IUser, ILoginPassword

@Serializable
sealed interface ISentMessage {
    val message: String
}

@Serializable
sealed interface IMessage : ISentMessage {
    val sentAt: LocalDateTime
    val user: IUser
}

@Serializable
sealed interface IYourMessage : IMessage

@Serializable
sealed interface INotification

@Serializable
sealed interface INotificationMessage : IMessage, INotification {
    val chatName: String
}

@Serializable
sealed interface IChat {
    val identifier: String
    val title: String
}

@Serializable
sealed interface IPrivateChat : IChat {
    val user: IUser
}

@Serializable
sealed interface IPage<T> {
    val next: Int?
    val previous: Int?
    val list: List<T>
}
