package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.Image
import com.gazim.gmessenger.domain.service.GMessengerSessionService

class UploadProfilePhotoUseCaseImpl(
    private val gMessengerSessionService: GMessengerSessionService,
) : UploadProfilePhotoUseCase {
    override suspend fun invoke(
        type: String,
        bytes: ByteArray,
    ): Image = gMessengerSessionService.uploadProfilePhoto(type, bytes)
}
