@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public data class User(
    val id: Uuid,
    val nickname: String,
    val username: String,
    val photo: Image?,
)
