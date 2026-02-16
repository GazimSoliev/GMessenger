@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.model

import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public data class Message(
    val id: Uuid,
    val message: String,
    val user: User,
    val sentAt: Instant,
)
