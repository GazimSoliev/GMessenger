package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.Clock
import java.time.LocalDateTime

object ChatAccountTable : UUIDTable("chat_account") {
    val idChat = reference("id_chat", ChatTable)
    val idAccount = reference("id_account", AccountTable)
    val createdAt = datetime("created_at").default(LocalDateTime.now(Clock.systemUTC()))
}
