@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.repository

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.model.User
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IChatRepository {
    suspend fun getChats(userId: Uuid): List<IChat>

    suspend fun getMembers(
        userId: Uuid,
        chatId: Uuid,
    ): List<User>

    suspend fun createChat(userIds: List<Uuid>): IChat?

    suspend fun getChat(
        userId: Uuid,
        chatId: Uuid,
    ): IChat?

    suspend fun existInChat(
        userId: Uuid,
        chatId: Uuid,
    ): Boolean
}

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

