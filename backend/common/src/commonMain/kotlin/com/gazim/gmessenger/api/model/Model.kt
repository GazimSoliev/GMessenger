package com.gazim.gmessenger.api.model

import kotlinx.datetime.LocalDateTime

interface IMessage {
    val id: String
    val message: String
    val sentAt: LocalDateTime
    val user: User
}

interface IChat {
    val id: String
    val title: String
}
