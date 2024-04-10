package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.*

interface IGetUserUseCase {
    suspend operator fun invoke(tokenId: UUID): User
}

interface IGetChatsUseCase {
    suspend operator fun invoke(user: User): List<IChat>
}

interface ISendMessageUseCase {
    suspend operator fun invoke(
        user: User,
        chat: IChat,
        message: MessageForm,
    )
}

interface IGetMessageFlowUseCase {
    suspend operator fun invoke(
        user: User,
        chat: IChat,
        limit: Int = 64,
        startFrom: Long? = null,
    ): Flow<Message>?
}

interface ILoginUseCase {
    suspend operator fun invoke(
        loginPassword: AuthenticationForm,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Token?
}

interface IRegisterUseCase {
    suspend operator fun invoke(account: RegistrationForm): Boolean
}

interface IFindUserUseCase {
    suspend operator fun invoke(username: String): List<User>
}

interface IGetChatUseCase {
    suspend operator fun invoke(
        user: User,
        chatId: UUID,
    ): IChat?
}

interface ICreateChatUseCase {
    suspend operator fun invoke(
        owner: User,
        users: List<User>,
    ): IChat?
}

interface IGetNotifications {
    operator fun invoke(user: User): Flow<Message>
}

interface IGetMessagesUseCase {
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
