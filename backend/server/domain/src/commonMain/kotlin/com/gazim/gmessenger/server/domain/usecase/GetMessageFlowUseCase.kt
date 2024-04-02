package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.IMessagingService
import kotlinx.coroutines.flow.Flow

class GetMessageFlowUseCase(private val messagingService: IMessagingService) : IGetMessageFlowUseCase {
    override suspend fun invoke(
        user: User,
        chat: IChat,
        limit: Int,
        startFrom: Long?,
    ): Flow<Message>? = messagingService.getMessageFlow(user, chat)
}
