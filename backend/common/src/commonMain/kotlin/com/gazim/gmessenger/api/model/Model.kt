package com.gazim.gmessenger.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

interface IMessage {
    val id: String
    val message: String
    val sentAt: LocalDateTime
    val user: User
}

@Serializable
sealed interface IChat {
    val id: String
    val title: String
}
