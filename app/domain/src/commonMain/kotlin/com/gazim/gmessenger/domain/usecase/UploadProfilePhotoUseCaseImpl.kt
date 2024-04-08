package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.Image
import com.gazim.gmessenger.domain.service.IGMessengerService

class UploadProfilePhotoUseCaseImpl(
    private val gMessengerService: IGMessengerService
) : UploadProfilePhotoUseCase {
    override suspend fun invoke(type: String, bytes: ByteArray): Image =
        gMessengerService.uploadProfilePhoto(type, bytes)
}