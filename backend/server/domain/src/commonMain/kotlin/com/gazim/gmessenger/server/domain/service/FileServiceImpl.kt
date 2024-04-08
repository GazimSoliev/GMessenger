package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.FileRepository
import java.util.*

class FileServiceImpl(
    private val fileRepository: FileRepository
) : FileService {
    override suspend fun getImageContent(photoId: UUID): ByteArray =
        fileRepository.getImageContent(photoId)

    override suspend fun uploadImage(user: User, type: String, content: ByteArray): Image =
        fileRepository.uploadImage(user, type, content)
}