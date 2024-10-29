package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.*

interface GetUserUseCase {
    suspend operator fun invoke(tokenId: UUID): User
}

interface GetChatsUseCase {
    suspend operator fun invoke(userId: UUID): List<IChat>
}

interface SendMessageUseCase {
    suspend operator fun invoke(
        userId: UUID,
        chatId: UUID,
        message: MessageForm,
    )
}

interface GetMessageFlowUseCase {
    suspend operator fun invoke(
        userId: UUID,
        chatId: UUID,
        limit: Int = 64,
        startFrom: Long? = null,
    ): Flow<Message>?
}

interface LoginUseCase {
    suspend operator fun invoke(
        loginPassword: AuthenticationForm,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Token?
}

interface RegisterUseCase {
    suspend operator fun invoke(account: RegistrationForm): Boolean
}

interface FindUserUseCase {
    suspend operator fun invoke(username: String): List<User>
}

interface GetChatUseCase {
    suspend operator fun invoke(
        userId: UUID,
        chatId: UUID,
    ): IChat?
}

interface CreateChatUseCase {
    suspend operator fun invoke(
        ownerId: UUID,
        userIds: List<UUID>,
    ): IChat?
}

interface GetNotifications {
    operator fun invoke(user: User): Flow<Message>
}

interface GetMessagesUseCase {
    suspend operator fun invoke(
        userId: UUID,
        chatId: UUID,
        key: MessagePageKey?,
    ): MessagePage
}

interface EditProfileUseCase {
    suspend operator fun invoke(
        userId: UUID,
        profileForm: ProfileForm,
    )
}

interface GetImageContentUseCase {
    suspend operator fun invoke(photoId: UUID): ByteArray
}

interface UploadProfilePhotoUseCase {
    suspend operator fun invoke(
        userId: UUID,
        type: String,
        content: ByteArray,
    ): Image
}
