package com.gazim.gmessenger.presentation.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class ImageUI(
    @Contextual
    val id: Uuid,
    val type: String,
)
