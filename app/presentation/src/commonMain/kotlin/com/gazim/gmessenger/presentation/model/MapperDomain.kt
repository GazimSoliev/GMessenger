package com.gazim.gmessenger.presentation.model

import com.gazim.gmessenger.domain.model.*

fun IUserModel.toUserUI(): IUserUI = UserUI(id = id, nickname = nickname, username = username)

fun IMessageModel.toMessageUI(): IFullMessageUI =
    if (this is IYourMessageModel) {
        YourMessageUI(message = message, sentAt = sentAt, user = user.toUserUI())
    } else {
        TheirMessageUI(message = message, sentAt = sentAt, user = user.toUserUI())
    }

fun IMessageUI.toSentMessageUI(): ISentMessageModel = SentMessageModel(message = message)

fun IChatModel.toChatUI(): IChatUI =
    if (this is IPrivateChatModel) {
        PrivateChatUI(
            identifier = id,
            title = title,
            chatName = user.nickname,
            chatLink = "@${user.username}",
            user = user.toUserUI(),
        )
    } else {
        ChatUI(
            identifier = id,
            title = title,
            chatName = title,
            chatLink = "",
        )
    }

fun IUserUI.toUserModel() = UserModel(id = id, nickname = nickname, username = username)

fun IChatUI.toChatModel(): IChatModel =
    if (this is IPrivateChatUI) {
        PrivateChatModel(
            id = identifier,
            title = title,
            user = user.toUserModel(),
        )
    } else {
        ChatModel(
            id = identifier,
            title = title,
        )
    }
