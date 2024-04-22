package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.GMessengerService

class GetImageContentUseCaseImpl(
    private val gMessengerService: GMessengerService,
) : GetImageContentUseCase {
    override suspend fun invoke(photoId: String): ByteArray = gMessengerService.getImageContent(photoId)
}
