package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.Image
import com.gazim.gmessenger.domain.service.GMessengerService

class UploadProfilePhotoUseCaseImpl(
    private val gMessengerService: GMessengerService,
) : UploadProfilePhotoUseCase {
    override suspend fun invoke(
        type: String,
        bytes: ByteArray,
    ): Image = gMessengerService.uploadProfilePhoto(type, bytes)
}
