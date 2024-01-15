package com.gazim.gmessenger.backend.common.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("NotificationMessage")
@Serializable
data class NotificationMessagePresent(
    override val chatName: String,
    override val user: IUserPresent,
    override val message: String,
    override val sentAt: LocalDateTime,
) : INotificationMessagePresent
