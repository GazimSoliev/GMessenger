package com.gazim.gmessenger.server.database.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object TokenTable : IntIdTable("token") {
    val createdAt = datetime("created_at")
    val expiredAt = datetime("expired_at")
    val idAccount = reference("id_account", AccountTable)
}
