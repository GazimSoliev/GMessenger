package com.gazim.gmessenger.server.domain.model

import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Message(
    val id: Uuid,
    val message: String,
    val user: User,
    val sentAt: LocalDateTime,
)
