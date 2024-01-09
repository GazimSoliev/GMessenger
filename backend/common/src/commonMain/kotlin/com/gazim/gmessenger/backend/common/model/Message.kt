package com.gazim.gmessenger.backend.common.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Message")
data class Message(
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: IUser,
) : IMessage
