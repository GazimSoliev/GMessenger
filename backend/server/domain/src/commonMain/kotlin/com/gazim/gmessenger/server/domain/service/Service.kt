package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.*

interface IUserService {
    suspend fun findUser(username: String): List<User>

    suspend fun getUser(tokenId: UUID): User

    suspend fun editProfile(
        user: User,
        profileForm: ProfileForm,
    )
}

interface IAuthorizationService {
    suspend fun login(
        loginPassword: AuthenticationForm,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Token?

    suspend fun register(account: RegistrationForm): Boolean
}

interface IChatService {
    suspend fun getChats(
        user: User,
        limit: Int = 64,
        startFrom: Long? = null,
    ): List<IChat>

    suspend fun getMembers(
        user: User,
        chat: IChat,
    ): List<User>

    suspend fun createChat(user: List<User>): IChat?

    suspend fun getChat(
        user: User,
        chatId: UUID,
    ): IChat?
}

interface IMessagingService {
    suspend fun sendMessage(
        user: User,
        chat: IChat,
        messageForm: MessageForm,
    )

    suspend fun getMessages(
        user: User,
        chat: IChat,
        key: MessagePageKey?,
    ): MessagePage

    suspend fun getMessageFlow(
        user: User,
        chat: IChat,
    ): Flow<Message>?
}
