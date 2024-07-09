package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.LoginTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class LoginEntity(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<LoginEntity>(LoginTable)

    var login by LoginTable.login
    var createdAt by LoginTable.createdAt
    var account by AccountEntity referencedOn LoginTable.idAccount
}
