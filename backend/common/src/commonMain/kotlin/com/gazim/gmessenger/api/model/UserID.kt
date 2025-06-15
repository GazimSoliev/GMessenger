@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.api.model

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class UserID(
    val id: Uuid,
)
