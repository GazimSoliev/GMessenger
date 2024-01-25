package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.coroutines.flow.Flow

interface IGetUserUseCase {
    operator fun invoke(id: Long): IUser
}

interface IGetChatsUseCase {
    operator fun invoke(user: IUser): List<IChat>
}

interface ISendMessageUseCase {
    operator fun invoke(
        user: IUser,
        chat: IChat,
        message: ISentMessage,
    )
}

interface IGetMessagesUseCase {
    operator fun invoke(
        user: IUser,
        chat: IChat,
        limit: Int = 64,
        startFrom: Long? = null,
    ): Flow<List<IMessage>>
}

interface ILoginUseCase {
    operator fun invoke(loginPassword: ILoginPassword): Long?
}

interface IRegisterUseCase {
    operator fun invoke(loginPassword: IAccount): Boolean
}

interface IFindUserUseCase {
    operator fun invoke(username: String): List<IUser>
}

interface IGetChatUseCase {
    operator fun invoke(
        user: IUser,
        chatId: Long,
    ): IChat
}

interface ICreateChatUseCase {
    operator fun invoke(
        owner: IUser,
        users: List<IUser>,
    ): IChat?
}

interface IGetNotifications {
    operator fun invoke(user: IUser): Flow<IMessage>
}
