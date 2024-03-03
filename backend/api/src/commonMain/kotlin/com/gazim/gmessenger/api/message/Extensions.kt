package com.gazim.gmessenger.api.message

import com.gazim.gmessenger.api.model.Message

fun Message.toMyMessage() =
    MyMessage(
        id = id,
        message = message,
        sentAt = sentAt,
        user = user,
    )
