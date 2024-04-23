package com.gazim.gmessenger.presentation.model

import com.gazim.gmessenger.domain.model.*

fun User.toUserUI(): IUserUI = UserUI(id = id, nickname = nickname, username = username)

fun IMessage.toMessageUI(): IFullMessageUI =
    if (this is YourMessage) {
        YourMessageUI(
            id = id,
            message = message,
            sentAt = sentAt,
            user = user.toUserUI(),
        )
    } else {
        TheirMessageUI(
            id = id,
            message = message,
            sentAt = sentAt,
            user = user.toUserUI(),
        )
    }

fun IMessageUI.toSentMessageUI() = SentMessage(message = message)

fun IChatModel.toChatUI(): IChatUI =
    if (this is IPrivateChatModel) {
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

fun IUserUI.toDomain() = User(id = id, nickname = nickname, username = username, null)

fun IChatUI.toChatModel(): IChatModel =
    if (this is IPrivateChatUI) {
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
