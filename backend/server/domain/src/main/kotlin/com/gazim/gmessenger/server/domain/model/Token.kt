package com.gazim.gmessenger.server.domain.model

import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Token(
    val id: Uuid,
    val expiredAt: LocalDateTime,
)
