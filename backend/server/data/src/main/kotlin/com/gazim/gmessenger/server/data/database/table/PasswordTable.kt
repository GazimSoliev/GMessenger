package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object PasswordTable : UUIDTable("password") {
    val idAccount = reference("id_account", AccountTable)
    val password = binary("password", 32)
    val createdAt = timestamp("created_at")
}
