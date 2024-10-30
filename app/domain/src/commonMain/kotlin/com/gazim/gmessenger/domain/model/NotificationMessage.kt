package com.gazim.gmessenger.domain.model

import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class NotificationMessage(
    val id: Uuid,
    val message: String,
    val sentAt: LocalDateTime,
    val user: User,
    val chatName: String,
) : Notification
