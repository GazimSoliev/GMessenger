@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.presentation.model

import com.gazim.gmessenger.domain.model.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
            sentAt = sentAt.toLocalDateTime(timeZone),
            localSentAt = sentAt.toLocalDateTime(timeZone),
            user = user.toUI(),
        )
    } else {
        TheirMessageUI(
            id = id,
            message = message,
            sentAt = sentAt.toLocalDateTime(timeZone),
            localSentAt = sentAt.toLocalDateTime(timeZone),
            user = user.toUI(),
        )
    }

fun Iterable<IMessage>.toUI(timeZone: TimeZone = TimeZone.currentSystemDefault()) = map { it.toUI(timeZone) }

// TODO
val messageSentAtFormatter by lazy {
    LocalDateTime.Format {
        hour()
        char(':')
        minute()
        char(' ')
        dayOfMonth()
        char('.')
        monthNumber()
        char('.')
        year()
    }
}

fun IChat.toUI(timeZone: TimeZone = TimeZone.currentSystemDefault()): ChatUI {
    val title: String
    val link: String
    val image: Uuid?
    if (this is PrivateChat) {
        title = user.nickname
        link = "@${user.username}"
        image = user.photo?.id
    } else {
        title = this@toUI.title
        link = ""
        image = null
    }
    val lastMessageSentAt = lastMessage?.sentAt?.toLocalDateTime(timeZone)
    return ChatUI(
        identifier = id,
        title = title,
        link = link,
        image = image,
        firstLetter = title.firstOrNull()?.uppercaseChar() ?: ' ',
        lastMessage = lastMessage?.message ?: "",
        lastMessageDateTime = lastMessageSentAt?.let { messageSentAtFormatter.format(it) } ?: "",
    )
}

fun List<IChat>.toUI(timeZone: TimeZone = TimeZone.currentSystemDefault()): List<ChatUI> =
    map { chat ->
        chat.toUI(timeZone)
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

fun List<GMessengerServer>.toUI() =
    mapIndexed { i, it ->
        ServerInfoUI(id = i, server = it.title, host = it.host, isSecure = it.isSecure, ping = "-", selected = false)
    }
