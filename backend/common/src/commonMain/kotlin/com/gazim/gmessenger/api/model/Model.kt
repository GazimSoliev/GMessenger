@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IMessage {
    val id: Uuid
    val message: String
    val sentAt: LocalDateTime
    val user: User
}

@Serializable
sealed interface IChat {
    val id: Uuid
    val title: String
}

interface Page<Key, Data> {
    val data: Data
    val next: Key?
    val prev: Key?
}
