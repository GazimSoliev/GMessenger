package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IChat
import com.gazim.gmessenger.server.domain.service.IChatService
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class CreateChatUseCaseImpl(
    private val chatService: IChatService,
) : CreateChatUseCase {
    override suspend fun invoke(ownerId: Uuid, userIds: List<Uuid>): IChat? = chatService.createChat(userIds + ownerId)
}
