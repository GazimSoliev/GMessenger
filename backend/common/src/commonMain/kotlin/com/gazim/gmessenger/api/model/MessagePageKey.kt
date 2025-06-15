package com.gazim.gmessenger.api.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class MessagePageKey(
    val start: Instant,
    val end: Instant,
)
