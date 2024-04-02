package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

object ChatAccountTable : UUIDTable("chat_account") {
    val idChat = reference("id_chat", ChatTable)
    val idAccount = reference("id_account", AccountTable)
    val createdAt = datetime("created_at")
}
