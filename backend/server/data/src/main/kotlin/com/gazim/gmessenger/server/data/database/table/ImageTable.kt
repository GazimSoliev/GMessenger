package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object ImageTable : UUIDTable("photo") {
    val idAccount = reference("id_account", AccountTable)
    val content = largeText("content")
    val type = varchar("type", 8)
    val createdAt = timestamp("created_at")
}
