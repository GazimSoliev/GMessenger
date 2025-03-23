package com.gazim.gmessenger.api.model

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Image(
    val id: Uuid,
    val type: String,
)
