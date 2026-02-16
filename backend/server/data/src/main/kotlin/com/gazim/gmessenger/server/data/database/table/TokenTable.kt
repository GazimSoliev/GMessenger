package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.timestamp

internal object TokenTable : UUIDTable("token") {
    val idAccount = reference("id_account", AccountTable)
    val expiredAt = timestamp("expired_at")
    val createdAt = timestamp("created_at")
}
