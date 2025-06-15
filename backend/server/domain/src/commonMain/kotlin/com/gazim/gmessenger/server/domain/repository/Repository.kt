@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.repository

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserRepository {
    suspend fun insertAndGetId(
        nickname: String,
        username: String,
        createdAt: Instant,
    ): Uuid

    suspend fun checkUserExist(username: String): Boolean

    suspend fun findUserByLoginAndPassword(
        login: ByteArray,
        password: ByteArray,
    ): Uuid?

    suspend fun findByUsername(
        username: String,
        limit: Int,
    ): List<User>

    suspend fun editProfile(
        userId: Uuid,
        nickname: String,
        username: String,
    )

    suspend fun setProfilePhoto(
        userId: Uuid,
        imageId: Uuid,
        createdAt: Instant,
    )

    suspend fun getUserById(userId: Uuid): User
}

interface LoginRepository {
    suspend fun insert(
        login: ByteArray,
        userId: Uuid,
        createdAt: Instant,
    )
}

interface PasswordRepository {
    suspend fun insert(
        password: ByteArray,
        userId: Uuid,
        createdAt: Instant,
    )
}

interface TokenRepository {
    suspend fun insertAndGetToken(
        createdAt: Instant,
        expiredAt: Instant,
        userId: Uuid,
    ): Token

    suspend fun getUserId(tokenId: Uuid): Uuid
}

interface FileRepository {
    suspend fun getBase64Image(photoId: Uuid): String

    suspend fun uploadAndGetImage(
        userId: Uuid,
        type: String,
        base64Image: String,
        createdAt: Instant,
    ): Image
}

interface ChatRepository {
    suspend fun getChatsByUser(
        userId: Uuid,
        size: Int,
        page: Int,
    ): List<IChat>

    suspend fun getMembers(chatId: Uuid): List<User>

    suspend fun createChat(
        title: String,
        createdAt: Instant,
    ): IChat?

    suspend fun getChat(chatId: Uuid): IChat?

    suspend fun existInChat(
        userId: Uuid,
        chatId: Uuid,
    ): Boolean

    suspend fun addUserInChat(
        userId: Uuid,
        chatId: Uuid,
        createdAt: Instant,
    )
}

interface MessageRepository {
    suspend fun sendMessage(
        userId: Uuid,
        chatId: Uuid,
        message: String,
        sentAt: Instant,
    ): Message

    suspend fun getMessages(
        chatId: Uuid,
        start: Instant,
        end: Instant,
    ): List<Message>

    suspend fun nextPage(
        chatId: Uuid,
        offset: Long,
        start: Instant,
    ): Instant?

    suspend fun prevPage(
        chatId: Uuid,
        offset: Long,
        end: Instant,
    ): Instant?
}
