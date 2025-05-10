package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.service.UserService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class UploadProfilePhotoUseCaseImpl(
    private val userService: UserService,
) : UploadProfilePhotoUseCase {
    override suspend fun invoke(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image = userService.uploadProfilePhoto(userId, type, content)
}
