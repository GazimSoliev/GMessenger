@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ImageEntity
import com.gazim.gmessenger.server.data.extensions.get
import com.gazim.gmessenger.server.data.mapper.toImage
import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.repository.FileRepository
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public class FileRepositoryImpl : FileRepository {
    override suspend fun getBase64Image(photoId: Uuid): String = ImageEntity[photoId].content

    override suspend fun uploadAndGetImage(
        userId: Uuid,
        type: String,
        base64Image: String,
        createdAt: Instant,
    ): Image {
        val account = AccountEntity[userId]
        val image =
            ImageEntity.new {
                this.account = account
                this.type = type
                this.content = base64Image
                this.createdAt = createdAt
            }
        return image.toImage()
    }
}
