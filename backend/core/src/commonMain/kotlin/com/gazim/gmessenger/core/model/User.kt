@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.core.model

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
public data class User(
    val id: Uuid,
    val nickname: String,
    val username: String,
    val photo: Image?,
)
