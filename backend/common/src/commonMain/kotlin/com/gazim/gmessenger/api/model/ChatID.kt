@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.api.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ChatID(
    val id: Uuid,
)
