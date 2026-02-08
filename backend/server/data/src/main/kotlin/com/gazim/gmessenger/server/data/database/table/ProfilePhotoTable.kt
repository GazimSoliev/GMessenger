package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.timestamp

object ProfilePhotoTable : UUIDTable("profile_photo") {
    val idAccount = reference("id_account", AccountTable)
    val idImage = reference("id_image", ImageTable)
    val createdAt = timestamp("created_at")
}
