package com.gazim.gmessenger.app.model

import com.gazim.gmessenger.domain.model.*

fun IUserModel.toUserUI(): IUserUI = UserUI(nickname = nickname, username = username)

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
            identifier = identifier,
            title = title,
            chatName = user.nickname,
            chatLink = "@${user.username}",
            user = user.toUserUI(),
        )
    } else {
        ChatUI(
            identifier = identifier,
            title = title,
            chatName = title,
            chatLink = "",
        )
    }

fun IUserUI.toUserModel() = UserModel(nickname = nickname, username = username)

fun IChatUI.toChatModel(): IChatModel =
    if (this is IPrivateChatUI) {
        PrivateChatModel(
            identifier = identifier,
            title = title,
            user = user.toUserModel(),
        )
    } else {
        ChatModel(
            identifier = identifier,
            title = title,
        )
    }
