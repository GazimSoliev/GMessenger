package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.GMessengerSessionService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class GetImageContentUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : GetImageContentUseCase {
    override suspend fun invoke(photoId: Uuid): Result<ByteArray> =
        runCatching {
            gMessengerSessionService.getImageContent(photoId)
        }
}
