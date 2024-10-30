@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.presentation.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed interface IMessageItemUI

sealed interface IMessageUI : IMessageItemUI {
    val id: Uuid
    val message: String
    val sentAt: LocalDateTime
    val localSentAt: LocalDateTime
    val user: UserUI
}

@Serializable
sealed interface IChatUI {
    val identifier: Uuid
    val title: String
    val chatName: String
    val chatLink: String
    val image: Uuid?
}
