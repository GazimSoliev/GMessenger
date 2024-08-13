package com.gazim.gmessenger.server.domain.repository

import com.gazim.gmessenger.server.domain.model.*
import java.time.LocalDateTime
import java.util.*

interface IUserRepository {
//    suspend fun insert(user: User): Boolean

    suspend fun findByUsername(
        username: String,
        limit: Int,
    ): List<User>

    suspend fun getUser(tokenId: UUID): User

    suspend fun editProfile(
        userId: UUID,
        profileForm: ProfileForm,
    )

    suspend fun setProfilePhoto(
        userId: UUID,
        imageId: UUID,
    )
}

interface ILoginRegisterRepository {
    suspend fun login(
        loginPassword: AuthenticationForm,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Token?

    suspend fun register(account: RegistrationForm): Boolean
}

interface IChatRepository {
    suspend fun getChats(userId: UUID): List<IChat>

    suspend fun getMembers(
        userId: UUID,
        chatId: UUID,
    ): List<User>

    suspend fun createChat(userIds: List<UUID>): IChat?

    suspend fun getChat(
        userId: UUID,
        chatId: UUID,
    ): IChat?

    suspend fun existInChat(
        userId: UUID,
        chatId: UUID,
    ): Boolean
}

interface IMessageRepository {
    suspend fun sendMessage(
        userId: UUID,
        chatId: UUID,
        message: MessageForm,
    ): Message

    suspend fun getMessages(
        chatId: UUID,
        start: LocalDateTime,
        end: LocalDateTime,
    ): List<Message>

    suspend fun nextPage(
        chatId: UUID,
        offset: Long,
        start: LocalDateTime,
    ): LocalDateTime?

    suspend fun prevPage(
        chatId: UUID,
        offset: Long,
        end: LocalDateTime,
    ): LocalDateTime?
}

interface FileRepository {
    suspend fun getImageContent(photoId: UUID): ByteArray

    suspend fun uploadImage(
        userId: UUID,
        type: String,
        content: ByteArray,
    ): Image
}
