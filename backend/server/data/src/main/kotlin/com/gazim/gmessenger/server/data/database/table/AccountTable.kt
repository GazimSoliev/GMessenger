package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.Clock
import java.time.LocalDateTime

object AccountTable : UUIDTable("account") {
    val nickname = varchar("nickname", 128)
    val username = varchar("username", 128)
    val createdAt = datetime("created_at").default(LocalDateTime.now(Clock.systemUTC()))
}
