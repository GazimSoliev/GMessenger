package com.gazim.gmessenger.server.domain.model

import java.time.LocalDateTime
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Message(
    val id: Uuid,
    val message: String,
    val user: User,
    val sentAt: LocalDateTime,
)
