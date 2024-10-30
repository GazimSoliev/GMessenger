package com.gazim.gmessenger.domain.model

import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class YourMessage(
    override val id: Uuid,
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: User,
) : IMessage
