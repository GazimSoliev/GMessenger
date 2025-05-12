package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.repository.FileRepository
import kotlinx.datetime.Clock
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalEncodingApi::class)
class FileServiceImpl(
    private val fileRepository: FileRepository,
) : FileService {
    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun getImageContent(photoId: Uuid): ByteArray {
        val base64Image = fileRepository.getBase64Image(photoId)
        return Base64.decode(base64Image)
    }

    override suspend fun uploadImage(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image {
        val base64Image = Base64.encode(content)
        val createdAt = Clock.System.now()
        return fileRepository.uploadAndGetImage(
            userId = userId,
            type = type,
            base64Image = base64Image,
            createdAt = createdAt,
        )
    }
}
