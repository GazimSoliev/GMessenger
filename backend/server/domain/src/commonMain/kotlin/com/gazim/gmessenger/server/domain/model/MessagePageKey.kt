package com.gazim.gmessenger.server.domain.model

import kotlinx.datetime.LocalDateTime

data class MessagePageKey(
    val start: LocalDateTime,
    val end: LocalDateTime,
)
