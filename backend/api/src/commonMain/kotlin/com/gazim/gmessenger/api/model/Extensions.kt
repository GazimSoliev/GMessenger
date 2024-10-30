@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.api.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun Message.toMyMessage() =
    MyMessage(
        id = id,
        message = message,
        sentAt = sentAt,
        user = user,
    )

fun MessagePage.toMyPage(userId: Uuid) =
    MyMessagePage(
        data = data.map { if (it.user.id == userId) it.toMyMessage() else it },
        next = next,
        prev = prev,
    )
