package com.gazim.gmessenger.presentation.model

import kotlinx.datetime.LocalDateTime

data class YourMessageUI(
    override val id: String,
    override val message: String,
    override val sentAt: LocalDateTime,
    override val localSentAt: LocalDateTime,
    override val user: UserUI,
) : IMessageUI
