package com.gazim.gmessenger.server.database.model

import com.gazim.gmessenger.server.database.table.AccountTable
import com.gazim.gmessenger.server.model.IUser

fun IUser.toAccountEntity(): AccountEntity? = AccountEntity.find { AccountTable.username eq username }.singleOrNull()
