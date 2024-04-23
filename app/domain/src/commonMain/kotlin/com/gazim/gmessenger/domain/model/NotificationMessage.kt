package com.gazim.gmessenger.domain.model

import kotlinx.datetime.LocalDateTime

data class NotificationMessage(
    val id: String,
    val message: String,
    val sentAt: LocalDateTime,
    val user: User,
    val chatName: String,
) : Notification
