package com.gazim.gmessenger.server.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

object ChatAccountTable : IntIdTable("chat_account") {
    val idChat = reference("id_chat", ChatTable)
    val idAccount = reference("id_account", AccountTable)
}
