package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.ProfilePhotoTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.*

internal class ProfilePhotoEntity(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ProfilePhotoEntity>(ProfilePhotoTable)

    var account by AccountEntity referencedOn ProfilePhotoTable.idAccount
    var image by ImageEntity referencedOn ProfilePhotoTable.idImage
    var createdAt by ProfilePhotoTable.createdAt
}
