package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ImageEntity
import com.gazim.gmessenger.server.data.extensions.get
import com.gazim.gmessenger.server.data.mapper.toImage
import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.repository.FileRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalEncodingApi::class, ExperimentalUuidApi::class)
class FileRepositoryImpl : FileRepository {
    override suspend fun getBase64Image(photoId: Uuid) =
        ImageEntity[photoId].content

    override suspend fun uploadAndGetImage(
        userId: Uuid,
        type: String,
        base64Image: String,
        createdAt: Instant
    ): Image {
        val account = AccountEntity[userId]
        val image =
            ImageEntity.new {
                this.account = account
                this.type = type
                this.content = base64Image
                this.createdAt = createdAt.toLocalDateTime(TimeZone.UTC)
            }
        return image.toImage()
    }
}
