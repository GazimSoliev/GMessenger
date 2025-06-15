@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.AuthenticationForm
import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.MessageForm
import com.gazim.gmessenger.server.domain.model.MessagePage
import com.gazim.gmessenger.server.domain.model.MessagePageKey
import com.gazim.gmessenger.server.domain.model.ProfileForm
import com.gazim.gmessenger.server.domain.model.RegistrationForm
import com.gazim.gmessenger.server.domain.model.Token
import com.gazim.gmessenger.server.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserService {
    suspend fun findUser(username: String): List<User>

    suspend fun getUser(tokenId: Uuid): User

    suspend fun editProfile(
        userId: Uuid,
        profileForm: ProfileForm,
    )

    suspend fun uploadProfilePhoto(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image
}

interface AuthorizationService {
    suspend fun login(
        loginPassword: AuthenticationForm,
    ): Token?

    suspend fun register(account: RegistrationForm): Boolean
}

interface ChatService {
    suspend fun getChats(
        userId: Uuid,
        size: Int,
        page: Int
    ): List<IChat>

    suspend fun getMembers(
        userId: Uuid,
        chatId: Uuid,
    ): List<User>

    suspend fun createChat(userIds: List<Uuid>): IChat?

    suspend fun getChat(
        userId: Uuid,
        chatId: Uuid,
    ): IChat?
}

interface IMessagingService {
    suspend fun sendMessage(
        userId: Uuid,
        chatId: Uuid,
        messageForm: MessageForm,
    )

    suspend fun getMessages(
        userId: Uuid,
        chatId: Uuid,
        key: MessagePageKey?,
    ): MessagePage

    suspend fun getMessageFlow(
        userId: Uuid,
        chatId: Uuid,
    ): Flow<Message>?
}

interface FileService {
    suspend fun getImageContent(photoId: Uuid): ByteArray

    suspend fun uploadImage(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image
}

interface SHA256Service {
    fun encode(bytes: ByteArray): ByteArray
}
