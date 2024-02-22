package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.PasswordTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PasswordEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PasswordEntity>(PasswordTable)

    var password by PasswordTable.password
    var setAt by PasswordTable.setAt
    var account by AccountEntity referencedOn PasswordTable.idAccount
}
