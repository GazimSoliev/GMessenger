package com.gazim.gmessenger.presentation.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

sealed interface IMessageItemUI

sealed interface IMessageUI : IMessageItemUI {
    val id: String
    val message: String
    val sentAt: LocalDateTime
    val localSentAt: LocalDateTime
    val user: UserUI
}

@Serializable
sealed interface IChatUI {
    val identifier: String
    val title: String
    val chatName: String
    val chatLink: String
    val image: String?
}
