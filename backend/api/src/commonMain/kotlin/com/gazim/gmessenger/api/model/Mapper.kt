package com.gazim.gmessenger.api.model

fun IMessage.toYourMessage(): IYourMessage =
    YourMessage(
        message = message,
        sentAt = sentAt,
        user = user,
    )
