@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.domain.model

import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class NotificationMessage(
    val id: Uuid,
    val message: String,
    val sentAt: Instant,
    val user: User,
    val chatName: String,
) : Notification
