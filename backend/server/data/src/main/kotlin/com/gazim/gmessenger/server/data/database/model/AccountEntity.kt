package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.database.table.ChatAccountTable
import com.gazim.gmessenger.server.data.database.table.ProfilePhotoTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class AccountEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AccountEntity>(AccountTable)

    var nickname by AccountTable.nickname
    var username by AccountTable.username
    var createdAt by AccountTable.createdAt

    val chats by ChatEntity via ChatAccountTable
    val photos by ImageEntity via ProfilePhotoTable
}
