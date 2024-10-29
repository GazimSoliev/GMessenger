package com.gazim.gmessenger.server.domain.model

import java.time.LocalDateTime

data class MessagePageKey(
    val start: LocalDateTime,
    val end: LocalDateTime,
)
