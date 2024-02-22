package com.gazim.gmessenger.server.data.database.model

import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.domain.model.IUser

fun IUser.toAccountEntity(): AccountEntity? = AccountEntity.find { AccountTable.username eq username }.singleOrNull()
