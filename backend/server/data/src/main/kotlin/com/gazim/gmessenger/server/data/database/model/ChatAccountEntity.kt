package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ChatAccountEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ChatAccountEntity>(ChatAccountTable)

    var account by AccountEntity referencedOn ChatAccountTable.idAccount
    var chatEntity by ChatEntity referencedOn ChatAccountTable.idChat
}
