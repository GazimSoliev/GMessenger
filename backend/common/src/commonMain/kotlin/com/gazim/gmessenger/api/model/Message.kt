package com.gazim.gmessenger.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Message(
    @Contextual
    override val id: Uuid,
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: User,
) : IMessage
