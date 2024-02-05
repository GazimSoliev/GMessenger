package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object AccountTable : IntIdTable("account") {
    val nickname = varchar("nickname", 128)
    val username = varchar("username", 128)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
}
