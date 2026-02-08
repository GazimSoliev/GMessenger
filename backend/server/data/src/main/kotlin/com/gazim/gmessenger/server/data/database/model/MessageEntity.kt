package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.MessageTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.*

class MessageEntity(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MessageEntity>(MessageTable)

    var chatEntity by ChatEntity referencedOn MessageTable.idChat
    var message by MessageTable.message
    var sentAt by MessageTable.createdAt
    var account by AccountEntity referencedOn MessageTable.idAccount
}
