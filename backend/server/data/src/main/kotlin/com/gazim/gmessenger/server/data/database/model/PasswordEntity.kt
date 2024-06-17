package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.PasswordTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class PasswordEntity(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PasswordEntity>(PasswordTable)

    var password by PasswordTable.password
    var createdAt by PasswordTable.createdAt
    var account by AccountEntity referencedOn PasswordTable.idAccount
}
