package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.Clock
import java.time.LocalDateTime

object PasswordTable : UUIDTable("password") {
    val idAccount = reference("id_account", AccountTable)
    val password = binary("password", 1024)
    val createdAt = datetime("created_at").default(LocalDateTime.now(Clock.systemUTC()))
}
