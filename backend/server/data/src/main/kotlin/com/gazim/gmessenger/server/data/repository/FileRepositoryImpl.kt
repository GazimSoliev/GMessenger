package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ImageEntity
import com.gazim.gmessenger.server.data.extensions.nowInUTC
import com.gazim.gmessenger.server.data.mapper.toImage
import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.repository.FileRepository
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalEncodingApi::class, ExperimentalUuidApi::class)
class FileRepositoryImpl : FileRepository {
    override suspend fun getImageContent(photoId: Uuid): ByteArray =
        dbQuery { ImageEntity[photoId.toJavaUuid()].content }.let { Base64.decode(it) }

    override suspend fun uploadImage(
        userId: Uuid,
        type: String,
        content: ByteArray,
    ): Image =
        dbQuery {
            val account = AccountEntity[userId.toJavaUuid()]
            val image =
                ImageEntity.new {
                    this.account = account
                    this.type = type
                    this.content = Base64.encode(content)
                    createdAt = nowInUTC()
                }
            image.toImage()
        }
}
