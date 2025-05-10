package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object TokenTable : UUIDTable("token") {
    val idAccount = reference("id_account", AccountTable)
    val expiredAt = datetime("expired_at")
    val createdAt = datetime("created_at")
}
