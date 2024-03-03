package com.gazim.gmessenger.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MessageNotification(
    val chatName: String,
    val user: User,
    val message: String,
    val sentAt: LocalDateTime,
)
