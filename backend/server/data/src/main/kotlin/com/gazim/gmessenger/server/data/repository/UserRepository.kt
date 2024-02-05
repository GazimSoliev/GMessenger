package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.TokenEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.mapper.toAccount
import com.gazim.gmessenger.server.domain.model.IUser
import com.gazim.gmessenger.server.domain.repository.IUserRepository

class UserRepository : IUserRepository {
    override suspend fun insert(user: IUser): Boolean {
        dbQuery {
            AccountEntity.new {
                nickname = user.nickname
                username = user.username
            }
        }
        return true
    }

    override suspend fun findByUsername(username: String, limit: Int): List<IUser> =
        dbQuery {
            AccountEntity.find {
                AccountTable.username like "%$username%"
            }.limit(limit).map(AccountEntity::toAccount)
        }

    override suspend fun getUser(idToken: Int): IUser =
        dbQuery {
            TokenEntity[idToken].account.toUser()
        }

}