package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.*

interface IUserService {
    suspend fun findUser(username: String): List<User>

    suspend fun getUser(tokenId: UUID): User

    suspend fun editProfile(
        userId: UUID,
        profileForm: ProfileForm,
    )

    suspend fun uploadProfilePhoto(
        userId: UUID,
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
        userId: UUID,
        limit: Int = 64,
        startFrom: Long? = null,
    ): List<IChat>

    suspend fun getMembers(
        userId: UUID,
        chatId: UUID,
    ): List<User>

    suspend fun createChat(userIds: List<UUID>): IChat?

    suspend fun getChat(
        userId: UUID,
        chatId: UUID,
    ): IChat?
}

interface IMessagingService {
    suspend fun sendMessage(
        userId: UUID,
        chatId: UUID,
        messageForm: MessageForm,
    )

    suspend fun getMessages(
        userId: UUID,
        chatId: UUID,
        key: MessagePageKey?,
    ): MessagePage

    suspend fun getMessageFlow(
        userId: UUID,
        chatId: UUID,
    ): Flow<Message>?
}

interface FileService {
    suspend fun getImageContent(photoId: UUID): ByteArray

    suspend fun uploadImage(
        userId: UUID,
        type: String,
        content: ByteArray,
    ): Image
}
