package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import com.gazim.gmessenger.server.data.database.table.ChatTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.*

class ChatEntity(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ChatEntity>(ChatTable)

    var title by ChatTable.title
    var createdAt by ChatTable.createdAt
    var members by AccountEntity via ChatAccountTable
}
