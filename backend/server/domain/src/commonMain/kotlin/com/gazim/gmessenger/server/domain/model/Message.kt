package com.gazim.gmessenger.server.domain.model

import kotlinx.datetime.LocalDateTime

data class Message(
    override val message: String,
    override val user: IUser,
    override val sentAt: LocalDateTime
) : IMessage