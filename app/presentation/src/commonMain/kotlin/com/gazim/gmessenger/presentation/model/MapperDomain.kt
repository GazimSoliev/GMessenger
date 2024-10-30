@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.presentation.model

import com.gazim.gmessenger.domain.model.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi

fun User.toUI() =
    UserUI(
        id = id,
        nickname = nickname,
        username = username,
        photo = photo?.toUI(),
    )

fun Image.toUI() = ImageUI(id = id, type = type)

fun IMessage.toUI(timeZone: TimeZone = TimeZone.currentSystemDefault()) =
    if (this is YourMessage) {
        YourMessageUI(
            id = id,
            message = message,
            sentAt = sentAt,
            localSentAt = sentAt.toInstant(TimeZone.UTC).toLocalDateTime(timeZone),
            user = user.toUI(),
        )
    } else {
        MessageUI(
            id = id,
            message = message,
            sentAt = sentAt,
            localSentAt = sentAt.toInstant(TimeZone.UTC).toLocalDateTime(timeZone),
            user = user.toUI(),
        )
    }

fun Iterable<IMessage>.toUI(timeZone: TimeZone = TimeZone.currentSystemDefault()) = map { it.toUI(timeZone) }

fun IChat.toUI() =
    if (this is PrivateChat) {
        PrivateChatUI(
            identifier = id,
            title = title,
            chatName = user.nickname,
            chatLink = "@${user.username}",
            user = user.toUI(),
            image = user.photo?.id,
        )
    } else {
        ChatUI(
            identifier = id,
            title = title,
            chatName = title,
            chatLink = "",
            image = null,
        )
    }

fun ImageUI.toDomain() =
    Image(
        id = id,
        type = type,
    )

fun UserUI.toDomain() =
    User(
        id = id,
        nickname = nickname,
        username = username,
        photo = photo?.toDomain(),
    )

fun IChatUI.toDomain() =
    if (this is PrivateChatUI) {
        PrivateChat(
            id = identifier,
            title = title,
            user = user.toDomain(),
        )
    } else {
        Chat(
            id = identifier,
            title = title,
        )
    }

fun List<GMessengerServer>.toUI() =
    mapIndexed { i, it ->
        ServerInfoUI(id = i, server = it.title, host = it.host, isSecure = it.isSecure, ping = "-", selected = false)
    }
