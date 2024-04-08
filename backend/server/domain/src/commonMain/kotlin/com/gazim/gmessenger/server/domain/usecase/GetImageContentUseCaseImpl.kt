package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.service.FileService
import java.util.*

class GetImageContentUseCaseImpl(
    private val fileService: FileService
) : GetImageContentUseCase {
    override suspend fun invoke(photoId: UUID): ByteArray =
        fileService.getImageContent(photoId)
}