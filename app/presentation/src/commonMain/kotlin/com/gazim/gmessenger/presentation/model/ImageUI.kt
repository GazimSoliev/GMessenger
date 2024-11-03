package com.gazim.gmessenger.presentation.model

import kotlinx.serialization.Serializable
import org.example.com.gazim.gmessenger.serialization.UuidSerializer
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class ImageUI(
    @Serializable(UuidSerializer::class)
    val id: Uuid,
    val type: String,
)
