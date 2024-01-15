package com.gazim.gmessenger.backend.common.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
sealed interface IUserPresent {
    val nickname: String
    val username: String
}

@Serializable
sealed interface ILoginPresent {
    val login: String
}

@Serializable
sealed interface IPasswordPresent {
    val password: String
}

@Serializable
sealed interface ILoginPasswordPresent : ILoginPresent, IPasswordPresent

@Serializable
sealed interface IAccountPresent : IUserPresent, ILoginPasswordPresent

@Serializable
sealed interface ISentMessagePresent {
    val message: String
}

@Serializable
sealed interface IMessagePresent : ISentMessagePresent {
    val sentAt: LocalDateTime
    val user: IUserPresent
}

@Serializable
sealed interface IYourMessagePresent : IMessagePresent

@Serializable
sealed interface INotificationPresent

@Serializable
sealed interface INotificationMessagePresent : IMessagePresent, INotificationPresent {
    val chatName: String
}

@Serializable
sealed interface IChatPresent {
    val identifier: String
    val title: String
}

@Serializable
sealed interface IPrivateChatPresent : IChatPresent {
    val user: IUserPresent
}

@Serializable
sealed interface IPagePresent<T> {
    val next: Int?
    val previous: Int?
    val list: List<T>
}
