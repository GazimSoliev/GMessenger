package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.ImageTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ImageEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ImageEntity>(ImageTable)

    var content by ImageTable.content
    var type by ImageTable.type
    var createdAt by ImageTable.createdAt
    var account by AccountEntity referencedOn ImageTable.idAccount
}