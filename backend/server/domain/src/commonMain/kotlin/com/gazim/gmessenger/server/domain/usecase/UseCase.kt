package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.*

interface IGetUserUseCase {
    operator fun invoke(id: Long): IUser
}

interface IGetChatsUseCase {
    operator fun invoke(user: IUser): List<IChat>
}

interface ISendMessageUseCase {
    operator fun invoke(user: IUser, chat: IChat, message: IMessage)
}

interface IGetMessagesUseCase {
    operator fun invoke(user: IUser, chat: IChat, limit: Int = 64, startFrom: Long? = null): List<IMessage>
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
    operator fun invoke(user: IUser, chatId: Long): IChat
}