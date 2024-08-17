package com.gazim.gmessenger.presentation.model

import com.gazim.gmessenger.domain.model.*

fun User.toUI() =
    UserUI(
        id = id,
        nickname = nickname,
        username = username,
        photo = photo?.toUI(),
    )

fun Image.toUI() = ImageUI(id = id, type = type)

fun IMessage.toMessageUI(): IMessageUI =
    if (this is YourMessage) {
        YourMessageUI(
            id = id,
            message = message,
            sentAt = sentAt,
            user = user.toUI(),
        )
    } else {
        MessageUI(
            id = id,
            message = message,
            sentAt = sentAt,
            user = user.toUI(),
        )
    }

fun IChat.toChatUI(): IChatUI =
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

fun IChatUI.toChatModel(): IChat =
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
