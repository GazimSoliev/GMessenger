package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.*
import kotlinx.coroutines.flow.Flow

fun interface OnLogInUseCase {
    suspend operator fun invoke(loginPassword: ILoginPasswordModel): Boolean
}

fun interface OnRegisterUseCase {
    suspend operator fun invoke(account: AccountModel): Boolean
}

fun interface GetOwnAccountUseCase {
    suspend operator fun invoke(): IUserModel
}

fun interface FilterUsersUseCase {
    suspend operator fun invoke(query: String): List<IUserModel>
}

fun interface GetChatsUseCase {
    suspend operator fun invoke(): List<IChatModel>
}

fun interface PassAuthUseCase {
    operator fun invoke(): Boolean
}

fun interface GetOwnUser {
    suspend operator fun invoke(): IUserModel
}

fun interface CreateChatUseCase {
    suspend operator fun invoke(user: IUserModel)
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
    suspend operator fun invoke()
}

fun interface CloseNotificationUseCase {
    suspend operator fun invoke()
}

fun interface GetNotificationsUseCase {
    suspend operator fun invoke(): Flow<INotificationModel>
}

fun interface GetSessionUseCase {
    operator fun invoke(): String?
}

fun interface GetChatUseCase {
    suspend operator fun invoke(chat: IChatModel): IChatWebSocketModel
}

interface GetMessagesUseCase {
    suspend operator fun invoke(
        chat: IChatModel,
        key: MessagePageKey?,
    ): MessagePage
}

interface EditProfileFormUseCase {
    suspend operator fun invoke(profileForm: ProfileForm)
}

interface UploadProfilePhotoUseCase {
    suspend operator fun invoke(
        type: String,
        bytes: ByteArray,
    ): Image
}

interface GetImageContentUseCase {
    suspend operator fun invoke(photoId: String): ByteArray
}
