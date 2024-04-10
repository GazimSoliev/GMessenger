package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

object ProfilePhotoTable : UUIDTable("profile_photo") {
    val idAccount = reference("id_account", AccountTable)
    val idImage = reference("id_image", ImageTable)
    val createdAt = datetime("created_at")
}
