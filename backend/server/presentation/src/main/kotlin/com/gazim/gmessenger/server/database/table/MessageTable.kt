package com.gazim.gmessenger.server.database.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object MessageTable : IntIdTable("message") {
    val message = varchar("message", 1024)
    val sentAt = datetime("sent_at").default(LocalDateTime.now())
    val idChat = reference("id_chat", ChatTable)
    val idAccount = reference("id_account", AccountTable)
}
