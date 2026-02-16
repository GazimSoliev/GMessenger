package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.*

internal class ChatAccountEntity(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ChatAccountEntity>(ChatAccountTable)

    var account by AccountEntity referencedOn ChatAccountTable.idAccount
    var chatEntity by ChatEntity referencedOn ChatAccountTable.idChat
    var createdAt by ChatAccountTable.createdAt
}
