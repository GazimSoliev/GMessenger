@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IChat {
    val id: Uuid
    val title: String
    val lastMessage: Message?
}
