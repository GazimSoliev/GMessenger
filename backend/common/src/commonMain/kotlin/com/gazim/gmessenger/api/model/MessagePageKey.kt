package com.gazim.gmessenger.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MessagePageKey(
    val start: LocalDateTime,
    val end: LocalDateTime,
)
