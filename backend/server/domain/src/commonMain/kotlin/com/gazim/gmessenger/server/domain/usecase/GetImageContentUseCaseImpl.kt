package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.service.FileService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
public class GetImageContentUseCaseImpl(
    private val fileService: FileService,
) : GetImageContentUseCase {
    override suspend fun invoke(photoId: Uuid): ByteArray = fileService.getImageContent(photoId)
}
