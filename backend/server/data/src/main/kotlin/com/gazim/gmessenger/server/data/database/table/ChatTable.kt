package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.time.ZoneOffset

object ChatTable : UUIDTable("chat") {
    val title = varchar("title", 64)
    val createdAt = datetime("created_at").default(LocalDateTime.now(ZoneOffset.UTC))
}
