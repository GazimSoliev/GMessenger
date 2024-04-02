package com.gazim.gmessenger.api.model

fun Message.toMyMessage() =
    MyMessage(
        id = id,
        message = message,
        sentAt = sentAt,
        user = user,
    )

fun MessagePage.toMyPage(userId: String) =
    MyMessagePage(
        data =
            data.map {
                println(it.user.id)
                println(userId)
                if (it.user.id == userId) it.toMyMessage() else it
            },
        next = next,
        prev = prev,
    )
