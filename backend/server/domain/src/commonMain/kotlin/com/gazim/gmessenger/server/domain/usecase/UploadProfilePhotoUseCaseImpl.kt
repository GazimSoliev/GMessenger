package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.service.UserService

class UploadProfilePhotoUseCaseImpl(
    private val userService: UserService
) : UploadProfilePhotoUseCase {
    override suspend fun invoke(user: User, type: String, content: ByteArray): Image =
        userService.uploadProfilePhoto(user, type, content)
}