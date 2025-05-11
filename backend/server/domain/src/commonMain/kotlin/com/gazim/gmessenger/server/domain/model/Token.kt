package com.gazim.gmessenger.server.domain.model

import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Token(
    val id: Uuid,
    val expiredAt: LocalDateTime,
)
