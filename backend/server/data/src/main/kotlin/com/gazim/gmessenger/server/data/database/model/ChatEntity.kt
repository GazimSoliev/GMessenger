package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import com.gazim.gmessenger.server.data.database.table.ChatTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ChatEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ChatEntity>(ChatTable)

    var title by ChatTable.title
    var createdAt by ChatTable.createdAt
    var members by AccountEntity via ChatAccountTable
}
