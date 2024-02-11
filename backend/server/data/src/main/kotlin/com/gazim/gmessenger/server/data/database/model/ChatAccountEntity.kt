package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ChatAccountEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ChatAccountEntity>(ChatAccountTable)

    var account by AccountEntity referencedOn ChatAccountTable.idAccount
    var chatEntity by ChatEntity referencedOn ChatAccountTable.idChat
}
