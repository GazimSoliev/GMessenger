package com.gazim.gmessenger.server.data.database.table

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.timestamp

internal object AccountTable : UUIDTable("account") {
    val nickname = varchar("nickname", 128)
    val username = varchar("username", 128)
    val createdAt = timestamp("created_at")
}
