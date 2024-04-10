package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.ImageEntity
import com.gazim.gmessenger.server.data.extensions.nowInUTC
import com.gazim.gmessenger.server.data.mapper.toAccountEntity
import com.gazim.gmessenger.server.data.mapper.toImage
import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.FileRepository
import java.util.*
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
class FileRepositoryImpl : FileRepository {
    override suspend fun getImageContent(photoId: UUID): ByteArray = dbQuery { ImageEntity[photoId].content }.let { Base64.decode(it) }

    override suspend fun uploadImage(
        user: User,
        type: String,
        content: ByteArray,
    ): Image =
        dbQuery {
            val account = user.toAccountEntity()
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
