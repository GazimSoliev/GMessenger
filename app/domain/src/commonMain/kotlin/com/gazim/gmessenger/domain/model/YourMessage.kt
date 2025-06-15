@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.domain.model

import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class YourMessage(
    override val id: Uuid,
    override val message: String,
    override val sentAt: Instant,
    override val user: User,
) : IMessage
