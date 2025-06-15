@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.api.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IMessage {
    val id: Uuid
    val message: String
    val sentAt: Instant
    val user: User
}

@Serializable
sealed interface IChat {
    val id: Uuid
    val title: String
    val lastMessage: Message?
}

interface Page<Key, Data> {
    val data: Data
    val next: Key?
    val prev: Key?
}
