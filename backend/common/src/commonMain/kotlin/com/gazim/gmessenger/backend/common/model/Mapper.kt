package com.gazim.gmessenger.backend.common.model

fun IMessage.toYourMessage(): IYourMessage =
    YourMessage(
        message = message,
        sentAt = sentAt,
        user = user,
    )
