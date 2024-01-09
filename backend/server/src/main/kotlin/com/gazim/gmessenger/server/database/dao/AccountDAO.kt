package com.gazim.gmessenger.server.database.dao

import com.gazim.gmessenger.server.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.database.model.AccountEntity

object AccountDAO : IAccountDAO {
    override suspend fun getSize(): Long = dbQuery { AccountEntity.count() }

    override suspend fun getAll(
        n: Int,
        offset: Long,
    ): List<AccountEntity> =
        dbQuery {
            AccountEntity.all().limit(n, offset).toList()
        }

    override suspend fun get(id: Int): AccountEntity = dbQuery { AccountEntity[id] }

    override suspend fun update(account: AccountEntity): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun insert(value: AccountEntity): AccountEntity =
        dbQuery {
//        AccountTable.insert {
//            it[nickname] = value.nickname
//            it[username] = value.username
//            it[createdAt] = value.createdAt.toJavaLocalDateTime()
//        }.resultedValues?.singleOrNull()?.toAccount()!!
            TODO()
        }

    override suspend fun delete(value: AccountEntity): Boolean {
        TODO("Not yet implemented")
    }
}
