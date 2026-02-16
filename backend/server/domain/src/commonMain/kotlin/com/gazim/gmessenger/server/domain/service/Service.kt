@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public interface UserService {
    public suspend fun findUser(username: String): List<User>

    public suspend fun getUser(tokenId: Uuid): User

    public suspend fun editProfile(
        userId: Uuid,
        profileForm: ProfileForm,
    )

    public suspend fun uploadProfilePhoto(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image
}

public interface AuthorizationService {
    public suspend fun login(loginPassword: AuthenticationForm): Token?

    public suspend fun register(account: RegistrationForm): Boolean
}

public interface ChatService {
    public suspend fun getChats(
        userId: Uuid,
        size: Int,
        page: Int,
    ): List<IChat>

    public suspend fun getMembers(
        userId: Uuid,
        chatId: Uuid,
    ): List<User>

    public suspend fun createChat(userIds: List<Uuid>): IChat

    public suspend fun getChat(
        userId: Uuid,
        chatId: Uuid,
    ): IChat
}

public interface MessagingService {
    public suspend fun sendMessage(
        userId: Uuid,
        chatId: Uuid,
        message: String,
    )

    public suspend fun getMessages(
        userId: Uuid,
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage

    public suspend fun getMessageFlow(
        userId: Uuid,
        chatId: Uuid,
    ): Flow<Message>
}

public interface FileService {
    public suspend fun getImageContent(photoId: Uuid): ByteArray

    public suspend fun uploadImage(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image
}

public interface SHA256Service {
    public fun encode(bytes: ByteArray): ByteArray
}
