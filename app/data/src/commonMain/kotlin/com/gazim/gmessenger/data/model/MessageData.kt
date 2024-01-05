package com.gazim.gmessenger.data.model

import kotlinx.datetime.LocalDateTime

data class MessageData(
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: IUserData,
) : IMessageData
