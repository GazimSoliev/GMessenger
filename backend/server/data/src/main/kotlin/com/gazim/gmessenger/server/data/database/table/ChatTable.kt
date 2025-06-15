package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object ChatTable : UUIDTable("chat") {
    val title = varchar("title", 64)
    val createdAt = timestamp("created_at")
}
