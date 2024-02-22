package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.LoginTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class LoginEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LoginEntity>(LoginTable)

    var login by LoginTable.login
    var setAt by LoginTable.setAt
    var account by AccountEntity referencedOn LoginTable.idAccount
}
