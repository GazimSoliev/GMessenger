package com.gazim.gmessenger.server.domain.model

import kotlinx.datetime.LocalDateTime

interface IUser {
    val nickname: String
    val username: String
}

interface IChat {
    val identifier: String
    val title: String
}

interface ISentMessage {
    val message: String
}

interface IMessage : ISentMessage {
    val user: IUser
    val sentAt: LocalDateTime
}

interface ILoginPassword {
    val login: String
    val password: String
}

interface IAccount : IUser, ILoginPassword
