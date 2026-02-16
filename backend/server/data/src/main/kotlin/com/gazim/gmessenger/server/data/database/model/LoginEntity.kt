package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.LoginTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.*

internal class LoginEntity(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<LoginEntity>(LoginTable)

    var login by LoginTable.login
    var createdAt by LoginTable.createdAt
    var account by AccountEntity referencedOn LoginTable.idAccount
}
