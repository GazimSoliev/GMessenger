package com.gazim.gmessenger.server.extensions

import com.gazim.gmessenger.backend.common.model.*
import com.gazim.gmessenger.server.domain.model.*

fun ILoginPasswordPresent.toDomain(): ILoginPassword =
    LoginPassword(
        login = login,
        password = password,
    )

fun IAccountPresent.toDomain(): IAccount =
    Account(
        nickname = nickname,
        username = username,
        login = login,
        password = password,
    )

fun IUser.toPresent(): IUserPresent =
    UserPresent(
        nickname = nickname,
        username = username,
    )

fun IUserPresent.toDomain(): IUser =
    User(
        nickname = nickname,
        username = username,
    )

fun IChat.toPresent(): IChatPresent =
    if (this is IPrivateChat) {
        PrivateChatPresent(
            identifier = identifier,
            title = title,
            user = user.toPresent(),
        )
    } else {
        ChatPresent(
            identifier = identifier,
            title = title,
        )
    }

fun ISentMessagePresent.toDomain(): ISentMessage = SentMessage(message = message)

fun IMessage.toPresent(): IMessagePresent = MessagePresent(message = message, sentAt = sentAt, user = user.toPresent())
