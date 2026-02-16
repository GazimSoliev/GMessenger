@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public data class Image(
    val id: Uuid,
    val type: String,
)
