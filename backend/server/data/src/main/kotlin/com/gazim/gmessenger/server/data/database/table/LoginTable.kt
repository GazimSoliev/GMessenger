package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.Clock
import java.time.LocalDateTime

object LoginTable : UUIDTable("login") {
    val idAccount = reference("id_account", AccountTable)
    val login = binary("login", 1024)
    val createdAt = datetime("created_at").default(LocalDateTime.now(Clock.systemUTC()))
}
