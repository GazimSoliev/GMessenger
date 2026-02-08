@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.core.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
public data class Message(
    override val id: Uuid,
    override val message: String,
    override val sentAt: Instant,
    override val user: User,
) : IMessage
