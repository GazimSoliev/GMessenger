package com.gazim.gmessenger.server.domain.model

import kotlinx.datetime.Instant

data class MessagePageKey(
    val start: Instant,
    val end: Instant,
)
