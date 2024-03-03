package com.gazim.gmessenger.api.message

import com.gazim.gmessenger.api.model.IMessage
import com.gazim.gmessenger.api.model.User
import kotlinx.datetime.LocalDateTime

data class MyMessage(
    override val id: String,
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: User,
) : IMessage
