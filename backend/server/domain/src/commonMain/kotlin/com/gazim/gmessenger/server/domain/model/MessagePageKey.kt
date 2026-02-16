package com.gazim.gmessenger.server.domain.model

import kotlin.time.Instant

public data class MessagePageKey(
    val start: Instant,
    val end: Instant,
)
