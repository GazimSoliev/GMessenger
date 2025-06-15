package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.MessagePageKey
import com.gazim.gmessenger.domain.service.GMessengerSessionService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetMessagesUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : GetMessagesUseCase {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun invoke(
        chatId: Uuid,
        key: MessagePageKey?,
    ) = runCatching {
        gMessengerSessionService.getMessages(chatId, key)
    }
}
