package com.gazim.gmessenger.domain.model

import kotlinx.datetime.LocalDateTime

data class Message(
    override val id: String,
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: User,
) : IMessage
