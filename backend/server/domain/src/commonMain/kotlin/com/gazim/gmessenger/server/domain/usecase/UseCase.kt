package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface IGetUserUseCase {
    suspend operator fun invoke(tokenId: Int): IUser
}

interface IGetChatsUseCase {
    suspend operator fun invoke(user: IUser): List<IChat>
}

interface ISendMessageUseCase {
    suspend operator fun invoke(
        user: IUser,
        chat: IChat,
        message: ISentMessage,
    )
}

interface IGetMessagesUseCase {
    suspend operator fun invoke(
        user: IUser,
        chat: IChat,
        limit: Int = 64,
        startFrom: Long? = null,
    ): Flow<IMessage>?
}

interface ILoginUseCase {
    suspend operator fun invoke(
        loginPassword: ILoginPassword,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Int?
}

interface IRegisterUseCase {
    suspend operator fun invoke(account: IAccount): Boolean
}

interface IFindUserUseCase {
    suspend operator fun invoke(username: String): List<IUser>
}

interface IGetChatUseCase {
    suspend operator fun invoke(
        user: IUser,
        chatId: Int,
    ): IChat?
}

interface ICreateChatUseCase {
    suspend operator fun invoke(
        owner: IUser,
        users: List<IUser>,
    ): IChat?
}

interface IGetNotifications {
    operator fun invoke(user: IUser): Flow<IMessage>
}
