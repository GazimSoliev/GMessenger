package com.gazim.gmessenger.presentation.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class UserUI(
    @Contextual
    val id: Uuid = Uuid.random(),
    val nickname: String = "",
    val username: String = "",
    val photo: ImageUI? = null,
)
