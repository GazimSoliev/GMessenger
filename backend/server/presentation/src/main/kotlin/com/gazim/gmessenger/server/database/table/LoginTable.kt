package com.gazim.gmessenger.server.database.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object LoginTable : IntIdTable("login") {
    val login = varchar("login", 128)
    val setAt = datetime("set_at").default(LocalDateTime.now())
    val idAccount = reference("id_account", AccountTable)
}
