package com.gazim.gmessenger.presentation.model

import kotlinx.serialization.Serializable
import org.example.com.gazim.gmessenger.serialization.UuidSerializer
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class UserUI(
    @Serializable(UuidSerializer::class)
    val id: Uuid = Uuid.random(),
    val nickname: String = "",
    val username: String = "",
    val photo: ImageUI? = null,
)
