package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.IGMessengerService

class GetImageContentUseCaseImpl(
    private val gMessengerService: IGMessengerService
) : GetImageContentUseCase {
    override suspend fun invoke(photoId: String): ByteArray =
        gMessengerService.getImageContent(photoId)
}