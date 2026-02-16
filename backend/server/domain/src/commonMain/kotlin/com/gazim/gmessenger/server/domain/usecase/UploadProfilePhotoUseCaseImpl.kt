package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.service.UserServiceImpl
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
public class UploadProfilePhotoUseCaseImpl(
    private val userService: UserServiceImpl,
) : UploadProfilePhotoUseCase {
    override suspend fun invoke(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image = userService.uploadProfilePhoto(userId, type, content)
}
