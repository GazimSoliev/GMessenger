@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.repository

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.model.ProfileForm
import com.gazim.gmessenger.server.domain.model.User
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IUserRepository {
//    suspend fun insert(user: User): Boolean

    suspend fun findByUsername(
        username: String,
        limit: Int,
    ): List<User>

    suspend fun getUser(tokenId: Uuid): User

    suspend fun editProfile(
        userId: Uuid,
        profileForm: ProfileForm,
    )

    suspend fun setProfilePhoto(
        userId: Uuid,
        imageId: Uuid,
    )
}

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

interface FileRepository {
    suspend fun getImageContent(photoId: Uuid): ByteArray

    suspend fun uploadImage(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image
}
