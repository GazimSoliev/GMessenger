package com.gazim.gmessenger.backend.common.model

fun IMessagePresent.toYourMessage(): IYourMessagePresent =
    YourMessagePresent(
        message = message,
        sentAt = sentAt,
        user = user,
    )
