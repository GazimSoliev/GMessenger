package com.gazim.gmessenger.presentation.model

import com.gazim.gmessenger.domain.model.*

fun User.toUserUI() = UserUI(id = id, nickname = nickname, username = username)

fun IMessage.toMessageUI(): IMessageUI =
    if (this is YourMessage) {
        YourMessageUI(
            id = id,
            message = message,
            sentAt = sentAt,
            user = user.toUserUI(),
        )
    } else {
        MessageUI(
            id = id,
            message = message,
            sentAt = sentAt,
            user = user.toUserUI(),
        )
    }

fun IChat.toChatUI(): IChatUI =
    if (this is PrivateChat) {
        PrivateChatUI(
            identifier = id,
            title = title,
            chatName = user.nickname,
            chatLink = "@${user.username}",
            user = user.toUserUI(),
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

fun UserUI.toDomain() = User(id = id, nickname = nickname, username = username, null)

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

fun GMessengerServer.toUI(ping: Long) = ServerInfoUI(server = title, url = url, ping = ping.toString())
