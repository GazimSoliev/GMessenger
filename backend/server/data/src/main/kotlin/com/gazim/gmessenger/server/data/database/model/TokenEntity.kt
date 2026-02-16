package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.TokenTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.*

internal class TokenEntity(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TokenEntity>(TokenTable)

    var createdAt by TokenTable.createdAt
    var expiredAt by TokenTable.expiredAt
    var account by AccountEntity referencedOn TokenTable.idAccount
}
