package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.GMessengerSessionService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateChatUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : CreateChatUseCase {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun invoke(userId: Uuid) =
        runCatching {
            gMessengerSessionService.createChat(userId)
        }
}
