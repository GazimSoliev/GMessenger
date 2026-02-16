@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.repository

import com.gazim.gmessenger.server.domain.model.*
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public interface UserRepository {
    public suspend fun insertAndGetId(
        nickname: String,
        username: String,
        createdAt: Instant,
    ): Uuid

    public suspend fun checkUserExist(username: String): Boolean

    public suspend fun findUserByLoginAndPassword(
        login: ByteArray,
        password: ByteArray,
    ): Uuid?

    public suspend fun findByUsername(
        username: String,
        limit: Int,
    ): List<User>

    public suspend fun editProfile(
        userId: Uuid,
        nickname: String,
        username: String,
    )

    public suspend fun setProfilePhoto(
        userId: Uuid,
        imageId: Uuid,
        createdAt: Instant,
    )

    public suspend fun getUserById(userId: Uuid): User
}

public interface LoginRepository {
    public suspend fun insert(
        login: ByteArray,
        userId: Uuid,
        createdAt: Instant,
    )
}

public interface PasswordRepository {
    public suspend fun insert(
        password: ByteArray,
        userId: Uuid,
        createdAt: Instant,
    )
}

public interface TokenRepository {
    public suspend fun insertAndGetToken(
        createdAt: Instant,
        expiredAt: Instant,
        userId: Uuid,
    ): Token

    public suspend fun getUserId(tokenId: Uuid): Uuid
}

public interface FileRepository {
    public suspend fun getBase64Image(photoId: Uuid): String

    public suspend fun uploadAndGetImage(
        userId: Uuid,
        type: String,
        base64Image: String,
        createdAt: Instant,
    ): Image
}

public interface ChatRepository {
    public suspend fun getChatsByUser(
        userId: Uuid,
        size: Int,
        page: Int,
    ): List<IChat>

    public suspend fun getMembers(chatId: Uuid): List<User>

    public suspend fun createChat(
        title: String,
        createdAt: Instant,
    ): IChat

    public suspend fun getChat(chatId: Uuid): IChat

    public suspend fun existInChat(
        userId: Uuid,
        chatId: Uuid,
    ): Boolean

    public suspend fun addUserInChat(
        userId: Uuid,
        chatId: Uuid,
        createdAt: Instant,
    )
}

public interface MessageRepository {
    public suspend fun sendMessage(
        userId: Uuid,
        chatId: Uuid,
        message: String,
        sentAt: Instant,
    ): Message

    public suspend fun getMessages(
        chatId: Uuid,
        start: Instant,
        end: Instant,
    ): List<Message>

    public suspend fun nextPage(
        chatId: Uuid,
        offset: Long,
        start: Instant,
    ): Instant?

    public suspend fun prevPage(
        chatId: Uuid,
        offset: Long,
        end: Instant,
    ): Instant?
}
