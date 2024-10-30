package com.gazim.gmessenger.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Image(
    val id: Uuid,
    val type: String,
)
