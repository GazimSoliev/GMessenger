@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.repository

import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.MessageForm
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IMessageRepository {
    suspend fun sendMessage(
        userId: Uuid,
        chatId: Uuid,
        message: MessageForm,
    ): Message

    suspend fun getMessages(
        chatId: Uuid,
        start: LocalDateTime,
        end: LocalDateTime,
    ): List<Message>

    suspend fun nextPage(
        chatId: Uuid,
        offset: Long,
        start: LocalDateTime,
    ): LocalDateTime?

    suspend fun prevPage(
        chatId: Uuid,
        offset: Long,
        end: LocalDateTime,
    ): LocalDateTime?
}

