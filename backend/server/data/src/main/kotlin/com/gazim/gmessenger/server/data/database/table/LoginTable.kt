package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.timestamp

object LoginTable : UUIDTable("login") {
    val idAccount = reference("id_account", AccountTable)
    val login = binary("login", 32)
    val createdAt = timestamp("created_at")
}
