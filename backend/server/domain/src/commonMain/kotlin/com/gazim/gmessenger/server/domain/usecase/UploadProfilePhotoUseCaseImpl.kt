package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.service.UserService
import java.util.*

class UploadProfilePhotoUseCaseImpl(
    private val userService: UserService,
) : UploadProfilePhotoUseCase {
    override suspend fun invoke(
        userId: UUID,
        type: String,
        content: ByteArray,
    ): Image = userService.uploadProfilePhoto(userId, type, content)
}
