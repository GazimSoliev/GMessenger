package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object PasswordTable : UUIDTable("password") {
    val idAccount = reference("id_account", AccountTable)
    val password = binary("password", 32)
    val createdAt = datetime("created_at")
}
