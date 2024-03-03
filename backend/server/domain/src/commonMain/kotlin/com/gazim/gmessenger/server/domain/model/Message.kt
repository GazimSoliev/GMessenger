package com.gazim.gmessenger.server.domain.model

import java.time.LocalDateTime
import java.util.*

data class Message(
    val id: UUID,
    val message: String,
    val user: User,
    val sentAt: LocalDateTime,
)
