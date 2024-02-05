package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AccountEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AccountEntity>(AccountTable)

    var nickname by AccountTable.nickname
    var username by AccountTable.username
    var createdAt by AccountTable.createdAt

    var chats by ChatEntity via ChatAccountTable
}
