package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object MessageTable : UUIDTable("message") {
    val idChat = reference("id_chat", ChatTable)
    val idAccount = reference("id_account", AccountTable)
    val message = varchar("message", 1024)
    val createdAt = timestamp("created_at")
}
