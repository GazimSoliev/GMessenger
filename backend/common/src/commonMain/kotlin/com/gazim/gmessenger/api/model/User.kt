package com.gazim.gmessenger.api.model

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class User(
    val id: Uuid,
    val nickname: String,
    val username: String,
    val photo: Image?,
)
