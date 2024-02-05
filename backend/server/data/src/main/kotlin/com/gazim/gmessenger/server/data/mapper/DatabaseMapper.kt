package com.gazim.gmessenger.server.data.mapper

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ChatEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.domain.model.*

fun AccountEntity.toUser() =
    User(
        nickname = nickname,
        username = username,
    )

fun IUser.toAccountEntity() = AccountEntity.find { AccountTable.username eq username }.singleOrNull()

fun ChatEntity.toChat(): IChat = Chat(identifier = id.value.toString(), title = title)

fun ChatEntity.toPrivateChat(partner: AccountEntity): IPrivateChat =
    PrivateChat(identifier = id.value.toString(), title = title, user = partner.toUser())

fun ChatEntity.toChat(currentUser: AccountEntity): IChat =
    when (members.count().toInt()) {
        1 -> toPrivateChat(currentUser)
        2 -> toPrivateChat(members.single { it != currentUser })
        else -> toChat()
    }