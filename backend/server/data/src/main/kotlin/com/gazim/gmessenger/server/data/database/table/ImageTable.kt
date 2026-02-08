package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.timestamp

object ImageTable : UUIDTable("photo") {
    val idAccount = reference("id_account", AccountTable)
    val content = largeText("content")
    val type = varchar("type", 8)
    val createdAt = timestamp("created_at")
}
