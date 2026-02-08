package com.gazim.gmessenger.core.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
public data class MessageNotification(
    val chatName: String,
    val user: User,
    val message: String,
    val sentAt: Instant,
)
