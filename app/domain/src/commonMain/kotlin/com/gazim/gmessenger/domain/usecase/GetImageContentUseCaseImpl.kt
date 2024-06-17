package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.GMessengerSessionService

class GetImageContentUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : GetImageContentUseCase {
    override suspend fun invoke(photoId: String) =
        runCatching {
            gMessengerSessionService.getImageContent(photoId)
        }
}
