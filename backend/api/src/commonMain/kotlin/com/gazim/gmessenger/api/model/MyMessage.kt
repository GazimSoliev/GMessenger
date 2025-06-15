package com.gazim.gmessenger.api.model

import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class MyMessage(
    override val id: Uuid,
    override val message: String,
    override val sentAt: Instant,
    override val user: User,
) : IMessage
