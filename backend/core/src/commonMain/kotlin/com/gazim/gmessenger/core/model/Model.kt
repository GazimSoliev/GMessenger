@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.core.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public interface IMessage {
    public val id: Uuid
    public val message: String
    public val sentAt: Instant
    public val user: User
}

@Serializable
public sealed interface IChat {
    public val id: Uuid
    public val title: String
    public val lastMessage: Message?
}

public interface Page<Key, Data> {
    public val data: Data
    public val next: Key?
    public val prev: Key?
}
