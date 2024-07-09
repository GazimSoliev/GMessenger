package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

fun interface LogOutUseCase {
    suspend operator fun invoke(): Result<Unit>
}

fun interface LogInUseCase {
    suspend operator fun invoke(loginPassword: AuthenticationForm): Result<Boolean>
}

fun interface RegisterUseCase {
    suspend operator fun invoke(account: RegistrationForm): Result<Boolean>
}

fun interface GetOwnAccountUseCase {
    suspend operator fun invoke(): Result<User>
}

fun interface FilterUsersUseCase {
    suspend operator fun invoke(query: String): Result<List<User>>
}

fun interface GetChatsUseCase {
    suspend operator fun invoke(): Result<List<IChat>>
}

fun interface PassAuthUseCase {
    operator fun invoke(): Boolean
}

fun interface GetOwnUser {
    suspend operator fun invoke(): Result<User>
}

fun interface CreateChatUseCase {
    suspend operator fun invoke(user: User): Result<Unit>
}

fun interface ValidateLoginUseCase {
    suspend operator fun invoke(login: String): Boolean
}

fun interface ValidatePasswordUseCase {
    suspend operator fun invoke(password: String): Boolean
}

fun interface ValidateNicknameUseCase {
    suspend operator fun invoke(nickname: String): Boolean
}

fun interface ValidateUsernameUseCase {
    suspend operator fun invoke(username: String): Boolean
}

fun interface OpenNotificationUseCase {
    suspend operator fun invoke(): Result<Unit>
}

fun interface CloseNotificationUseCase {
    suspend operator fun invoke(): Result<Unit>
}

fun interface GetNotificationsUseCase {
    suspend operator fun invoke(): Result<Flow<Notification>>
}

fun interface GetChatUseCase {
    suspend operator fun invoke(chat: IChat): Result<IChatWebSocketModel>
}

interface GetMessagesUseCase {
    suspend operator fun invoke(
        chat: IChat,
        key: MessagePageKey?,
    ): Result<MessagePage>
}

interface EditProfileFormUseCase {
    suspend operator fun invoke(profileForm: ProfileForm): Result<Unit>
}

interface UploadProfilePhotoUseCase {
    suspend operator fun invoke(
        type: String,
        bytes: ByteArray,
    ): Result<Image>
}

interface GetImageContentUseCase {
    suspend operator fun invoke(photoId: String): Result<ByteArray>
}

interface GetAvailableServersUseCase {
    suspend operator fun invoke(): List<GMessengerServer>
}

interface PingUseCase {
    suspend operator fun invoke(url: String): Duration
}

interface AddServerUseCase {
    suspend operator fun invoke(server: GMessengerServer)
}
