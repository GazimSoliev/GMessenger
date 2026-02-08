package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.timestamp

object ChatTable : UUIDTable("chat") {
    val title = varchar("title", 64)
    val createdAt = timestamp("created_at")
}
