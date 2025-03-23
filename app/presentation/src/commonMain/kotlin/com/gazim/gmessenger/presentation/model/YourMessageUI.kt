package com.gazim.gmessenger.presentation.model

import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class YourMessageUI(
    override val id: Uuid,
    override val message: String,
    override val sentAt: LocalDateTime,
    override val localSentAt: LocalDateTime,
    override val user: UserUI,
) : MessageUI
