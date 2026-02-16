package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.PasswordTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.*

internal class PasswordEntity(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PasswordEntity>(PasswordTable)

    var password by PasswordTable.password
    var createdAt by PasswordTable.createdAt
    var account by AccountEntity referencedOn PasswordTable.idAccount
}
