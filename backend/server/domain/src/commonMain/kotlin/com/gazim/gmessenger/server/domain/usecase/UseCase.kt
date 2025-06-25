@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface GetUserUseCase {
    suspend operator fun invoke(tokenId: Uuid): User
}

interface GetChatsUseCase {
    suspend operator fun invoke(userId: Uuid): List<IChat>
}

interface SendMessageUseCase {
    suspend operator fun invoke(
        userId: Uuid,
        chatId: Uuid,
        message: String,
    )
}

interface GetMessageFlowUseCase {
    suspend operator fun invoke(
        userId: Uuid,
        chatId: Uuid,
        limit: Int = 64,
        startFrom: Long? = null,
    ): Flow<Message>?
}

interface LoginUseCase {
    suspend operator fun invoke(loginPassword: AuthenticationForm): Token?
}

interface RegisterUseCase {
    suspend operator fun invoke(account: RegistrationForm): Boolean
}

interface FindUserUseCase {
    suspend operator fun invoke(username: String): List<User>
}

interface GetChatUseCase {
    suspend operator fun invoke(
        userId: Uuid,
        chatId: Uuid,
    ): IChat?
}

interface CreateChatUseCase {
    suspend operator fun invoke(
        ownerId: Uuid,
        userIds: List<Uuid>,
    ): IChat?
}

interface GetNotifications {
    operator fun invoke(user: User): Flow<Message>
}

interface GetMessagesUseCase {
    suspend operator fun invoke(
        userId: Uuid,
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage
}

interface EditProfileUseCase {
    suspend operator fun invoke(
        userId: Uuid,
        profileForm: ProfileForm,
    )
}

interface GetImageContentUseCase {
    suspend operator fun invoke(photoId: Uuid): ByteArray
}

interface UploadProfilePhotoUseCase {
    suspend operator fun invoke(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image
}
