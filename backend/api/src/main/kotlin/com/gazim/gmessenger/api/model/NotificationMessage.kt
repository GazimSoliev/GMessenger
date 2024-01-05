package com.gazim.gmessenger.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("NotificationMessage")
@Serializable
data class NotificationMessage(
    override val chatName: String,
    override val user: IUser,
    override val message: String,
    override val sentAt: LocalDateTime,
) : INotificationMessage
