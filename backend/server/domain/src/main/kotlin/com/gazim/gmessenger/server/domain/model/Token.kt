package com.gazim.gmessenger.server.domain.model

import java.time.LocalDateTime
import java.util.*

data class Token(
    val id: UUID,
    val expiredAt: LocalDateTime,
)
