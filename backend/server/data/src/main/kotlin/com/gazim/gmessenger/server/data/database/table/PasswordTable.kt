package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.timestamp

object PasswordTable : UUIDTable("password") {
    val idAccount = reference("id_account", AccountTable)
    val password = binary("password", 32)
    val createdAt = timestamp("created_at")
}
