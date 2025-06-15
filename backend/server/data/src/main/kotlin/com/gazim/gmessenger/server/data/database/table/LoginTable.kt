package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object LoginTable : UUIDTable("login") {
    val idAccount = reference("id_account", AccountTable)
    val login = binary("login", 32)
    val createdAt = timestamp("created_at")
}
