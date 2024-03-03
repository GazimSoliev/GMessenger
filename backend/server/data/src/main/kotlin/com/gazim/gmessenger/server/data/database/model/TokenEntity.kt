package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.TokenTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class TokenEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TokenEntity>(TokenTable)

    var createdAt by TokenTable.createdAt
    var expiredAt by TokenTable.expiredAt
    var account by AccountEntity referencedOn TokenTable.idAccount
}
