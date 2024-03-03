package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import com.gazim.gmessenger.server.data.database.table.ChatTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ChatEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ChatEntity>(ChatTable)

    var title by ChatTable.title
    var createdAt by ChatTable.createdAt
    var members by AccountEntity via ChatAccountTable
}
