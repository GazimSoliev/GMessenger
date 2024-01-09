package com.gazim.gmessenger.server.database.model

import com.gazim.gmessenger.server.database.table.TokenTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TokenEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TokenEntity>(TokenTable)

    var createdAt by TokenTable.createdAt
    var expiredAt by TokenTable.expiredAt
    var account by AccountEntity referencedOn TokenTable.idAccount
}
