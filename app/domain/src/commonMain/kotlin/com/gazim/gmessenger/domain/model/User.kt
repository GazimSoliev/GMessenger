package com.gazim.gmessenger.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class User(
    val id: Uuid,
    val nickname: String,
    val username: String,
    val photo: Image?,
)
