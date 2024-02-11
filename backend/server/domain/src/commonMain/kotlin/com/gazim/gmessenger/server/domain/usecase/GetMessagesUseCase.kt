package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.IMessage
import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.service.IMessagingService
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCase(private val messagingService: IMessagingService) : IGetMessagesUseCase {
    override suspend fun invoke(
        user: IUser,
        chat: IChat,
        limit: Int,
        startFrom: Long?,
    ): Flow<IMessage>? = messagingService.getMessageFlow(user, chat)
}
