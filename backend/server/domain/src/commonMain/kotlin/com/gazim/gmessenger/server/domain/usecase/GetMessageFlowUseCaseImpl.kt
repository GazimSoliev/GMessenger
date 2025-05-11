package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.service.IMessagingService
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class GetMessageFlowUseCaseImpl(
    private val messagingService: IMessagingService,
) : GetMessageFlowUseCase {
    override suspend fun invoke(
        userId: Uuid,
        chatId: Uuid,
        limit: Int,
        startFrom: Long?,
    ): Flow<Message>? = messagingService.getMessageFlow(userId, chatId)
}
