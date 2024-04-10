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
        user: User,
        profileForm: ProfileForm,
    )

    suspend fun setProfilePhoto(
        user: User,
        image: Image,
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
    suspend fun getChats(user: User): List<IChat>

    suspend fun getMembers(
        user: User,
        chat: IChat,
    ): List<User>

    suspend fun createChat(users: List<User>): IChat?

    suspend fun getChat(
        user: User,
        id: UUID,
    ): IChat?

    suspend fun existInChat(
        user: User,
        chat: IChat,
    ): Boolean
}

interface IMessageRepository {
    suspend fun sendMessage(
        user: User,
        chat: IChat,
        message: MessageForm,
    ): Message

    suspend fun getMessages(
        chat: IChat,
        start: LocalDateTime,
        end: LocalDateTime,
    ): List<Message>

    suspend fun nextPage(
        chat: IChat,
        offset: Long,
        start: LocalDateTime,
    ): LocalDateTime?

    suspend fun prevPage(
        chat: IChat,
        offset: Long,
        end: LocalDateTime,
    ): LocalDateTime?
}

interface FileRepository {
    suspend fun getImageContent(photoId: UUID): ByteArray

    suspend fun uploadImage(
        user: User,
        type: String,
        content: ByteArray,
    ): Image
}
