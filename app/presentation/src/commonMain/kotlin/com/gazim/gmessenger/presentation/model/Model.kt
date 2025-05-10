@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.presentation.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed interface MessageItemUI

sealed interface MessageUI : MessageItemUI {
    val id: Uuid
    val message: String
    val sentAt: LocalDateTime
    val localSentAt: LocalDateTime
    val user: UserUI
}

@Serializable
data class ChatUI(
    val identifier: Uuid,
    val title: String,
    val firstLetter: Char,
    val lastMessage: String,
    val lastMessageDateTime: String,
    val link: String,
    val image: Uuid?
)
