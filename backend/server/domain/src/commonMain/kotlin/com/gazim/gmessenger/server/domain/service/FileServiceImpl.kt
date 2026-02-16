package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.repository.DatabaseTransaction
import com.gazim.gmessenger.server.domain.repository.FileRepository
import com.gazim.gmessenger.server.domain.repository.invoke
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalEncodingApi::class)
public class FileServiceImpl(
    private val fileRepository: FileRepository,
    private val databaseTransaction: DatabaseTransaction,
) : FileService {
    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun getImageContent(photoId: Uuid): ByteArray {
        val base64Image = databaseTransaction { fileRepository.getBase64Image(photoId) }
        return Base64.decode(base64Image)
    }

    override suspend fun uploadImage(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image {
        val base64Image = Base64.encode(content)
        val createdAt = Clock.System.now()
        val image =
            databaseTransaction {
                fileRepository.uploadAndGetImage(
                    userId = userId,
                    type = type,
                    base64Image = base64Image,
                    createdAt = createdAt,
                )
            }
        return image
    }
}
