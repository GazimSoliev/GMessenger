package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.MessageTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class MessageEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MessageEntity>(MessageTable)

    var chatEntity by ChatEntity referencedOn MessageTable.idChat
    var message by MessageTable.message
    var sentAt by MessageTable.createdAt
    var account by AccountEntity referencedOn MessageTable.idAccount
}
