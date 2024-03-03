package com.gazim.gmessenger.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    override val id: String,
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: User,
) : IMessage
