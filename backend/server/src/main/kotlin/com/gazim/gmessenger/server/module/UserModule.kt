package com.gazim.gmessenger.server.module

import com.gazim.gmessenger.server.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.database.model.AccountEntity
import com.gazim.gmessenger.server.database.model.TokenEntity
import com.gazim.gmessenger.server.database.table.AccountTable
import com.gazim.gmessenger.server.extensions.toUser
import com.gazim.gmessenger.server.model.IUser

class UserModule : IUserModule {
    override suspend fun getUser(idToken: Int): IUser =
        dbQuery {
            TokenEntity[idToken].account.toUser()
        }

    override suspend fun findUser(username: String): List<IUser> =
        if (username.length !in 4..32) {
            emptyList()
        } else {
            dbQuery {
                AccountEntity.find {
                    AccountTable.username like "%$username%"
                }.limit(50).map(AccountEntity::toUser)
            }
        }
}
