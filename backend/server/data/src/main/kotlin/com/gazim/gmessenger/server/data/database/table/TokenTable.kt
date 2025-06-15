package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object TokenTable : UUIDTable("token") {
    val idAccount = reference("id_account", AccountTable)
    val expiredAt = timestamp("expired_at")
    val createdAt = timestamp("created_at")
}
