package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.MessageTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MessageEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MessageEntity>(MessageTable)

    var chatEntity by ChatEntity referencedOn MessageTable.idChat
    var message by MessageTable.message
    var sentAt by MessageTable.sentAt
    var account by AccountEntity referencedOn MessageTable.idAccount
}
