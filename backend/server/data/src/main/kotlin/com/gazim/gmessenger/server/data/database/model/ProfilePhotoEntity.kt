package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.ProfilePhotoTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ProfilePhotoEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ProfilePhotoEntity>(ProfilePhotoTable)

    var account by AccountEntity referencedOn ProfilePhotoTable.idAccount
    var image by ImageEntity referencedOn ProfilePhotoTable.idImage
    var createdAt by ProfilePhotoTable.createdAt
}
