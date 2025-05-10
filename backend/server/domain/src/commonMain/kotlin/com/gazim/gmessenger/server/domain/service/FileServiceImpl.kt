package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.repository.FileRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class FileServiceImpl(
    private val fileRepository: FileRepository,
) : FileService {
    override suspend fun getImageContent(photoId: Uuid): ByteArray = fileRepository.getImageContent(photoId)

    override suspend fun uploadImage(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image = fileRepository.uploadImage(userId, type, content)
}
