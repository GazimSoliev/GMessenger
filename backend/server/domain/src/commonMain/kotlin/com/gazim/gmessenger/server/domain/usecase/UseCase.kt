@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public interface GetUserUseCase {
    public suspend operator fun invoke(tokenId: Uuid): User
}

public interface GetChatsUseCase {
    public suspend operator fun invoke(userId: Uuid): List<IChat>
}

public interface SendMessageUseCase {
    public suspend operator fun invoke(
        userId: Uuid,
        chatId: Uuid,
        message: String,
    )
}

public interface GetMessageFlowUseCase {
    public suspend operator fun invoke(
        userId: Uuid,
        chatId: Uuid,
        limit: Int = 64,
        startFrom: Long? = null,
    ): Flow<Message>
}

public interface LoginUseCase {
    public suspend operator fun invoke(loginPassword: AuthenticationForm): Token?
}

public interface RegisterUseCase {
    public suspend operator fun invoke(account: RegistrationForm): Boolean
}

public interface FindUserUseCase {
    public suspend operator fun invoke(username: String): List<User>
}

public interface GetChatUseCase {
    public suspend operator fun invoke(
        userId: Uuid,
        chatId: Uuid,
    ): IChat
}

public interface CreateChatUseCase {
    public suspend operator fun invoke(
        ownerId: Uuid,
        userIds: List<Uuid>,
    ): IChat
}

public interface GetNotifications {
    public operator fun invoke(user: User): Flow<Message>
}

public interface GetMessagesUseCase {
    public suspend operator fun invoke(
        userId: Uuid,
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage
}

public interface EditProfileUseCase {
    public suspend operator fun invoke(
        userId: Uuid,
        profileForm: ProfileForm,
    )
}

public interface GetImageContentUseCase {
    public suspend operator fun invoke(photoId: Uuid): ByteArray
}

public interface UploadProfilePhotoUseCase {
    public suspend operator fun invoke(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image
}
