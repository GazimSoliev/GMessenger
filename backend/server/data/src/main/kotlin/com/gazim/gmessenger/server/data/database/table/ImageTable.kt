package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

object ImageTable: UUIDTable("photo") {
    val idAccount = reference("id_account", AccountTable)
    val content = largeText("content")
    val type = varchar("type", 8)
    val createdAt = datetime("created_at")
}