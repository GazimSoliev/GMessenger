package com.gazim.gmessenger.api.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ChatID(
    val id: Uuid,
)
