package com.gazim.gmessenger.core.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
public data class MessagePageKey(
    val start: Instant,
    val end: Instant,
)
