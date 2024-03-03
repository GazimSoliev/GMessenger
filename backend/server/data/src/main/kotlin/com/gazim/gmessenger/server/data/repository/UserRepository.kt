package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.TokenEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.mapper.toUser
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.IUserRepository
import java.util.*

class UserRepository : IUserRepository {
    override suspend fun insert(user: User): Boolean {
        dbQuery {
            AccountEntity.new {
                nickname = user.nickname
                username = user.username
            }
        }
        return true
    }

    override suspend fun findByUsername(
        username: String,
        limit: Int,
    ): List<User> =
        dbQuery {
            AccountEntity.find {
                AccountTable.username like "%$username%"
            }.limit(limit).map(AccountEntity::toUser)
        }

    override suspend fun getUser(tokenId: UUID): User =
        dbQuery {
            TokenEntity[tokenId].account.toUser()
        }
}
