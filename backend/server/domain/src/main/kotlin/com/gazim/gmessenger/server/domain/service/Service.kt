@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IUserService {
    suspend fun findUser(username: String): List<User>

    suspend fun getUser(tokenId: Uuid): User

    suspend fun editProfile(
        userId: Uuid,
        profileForm: ProfileForm,
    )

    suspend fun uploadProfilePhoto(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image
}

interface IAuthorizationService {
    suspend fun login(
        loginPassword: AuthenticationForm,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Token?

    suspend fun register(account: RegistrationForm): Boolean
}

interface IChatService {
    suspend fun getChats(
        userId: Uuid,
        limit: Int = 64,
        startFrom: Long? = null,
    ): List<IChat>

    suspend fun getMembers(
        userId: Uuid,
        chatId: Uuid,
    ): List<User>

    suspend fun createChat(userIds: List<Uuid>): IChat?

    suspend fun getChat(
        userId: Uuid,
        chatId: Uuid,
    ): IChat?
}

interface IMessagingService {
    suspend fun sendMessage(
        userId: Uuid,
        chatId: Uuid,
        messageForm: MessageForm,
    )

    suspend fun getMessages(
        userId: Uuid,
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage

    suspend fun getMessageFlow(
        userId: Uuid,
        chatId: Uuid,
    ): Flow<Message>?
}

interface FileService {
    suspend fun getImageContent(photoId: Uuid): ByteArray

    suspend fun uploadImage(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image
}
