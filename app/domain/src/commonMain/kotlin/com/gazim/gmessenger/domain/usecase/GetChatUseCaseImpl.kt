package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.GMessengerSessionService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GetChatUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : GetChatUseCase {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun invoke(chatUi: Uuid) =
        runCatching {
            gMessengerSessionService.getChat(chatUi)
        }
}
