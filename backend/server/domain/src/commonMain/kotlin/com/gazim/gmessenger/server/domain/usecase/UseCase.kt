package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.*

interface GetUserUseCase {
    suspend operator fun invoke(tokenId: UUID): User
}

interface GetChatsUseCase {
    suspend operator fun invoke(user: User): List<IChat>
}

interface SendMessageUseCase {
    suspend operator fun invoke(
        user: User,
        chat: IChat,
        message: MessageForm,
    )
}

interface GetMessageFlowUseCase {
    suspend operator fun invoke(
        user: User,
        chat: IChat,
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
        user: User,
        chatId: UUID,
    ): IChat?
}

interface CreateChatUseCase {
    suspend operator fun invoke(
        owner: User,
        users: List<User>,
    ): IChat?
}

interface GetNotifications {
    operator fun invoke(user: User): Flow<Message>
}

interface GetMessagesUseCase {
    suspend operator fun invoke(
        user: User,
        chat: IChat,
        key: MessagePageKey?,
    ): MessagePage
}

interface EditProfileUseCase {
    suspend operator fun invoke(
        user: User,
        profileForm: ProfileForm,
    )
}

interface GetImageContentUseCase {
    suspend operator fun invoke(photoId: UUID): ByteArray
}

interface UploadProfilePhotoUseCase {
    suspend operator fun invoke(
        user: User,
        type: String,
        content: ByteArray,
    ): Image
}
