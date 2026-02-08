@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.core.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public data class ChatID(
    val id: Uuid,
)
