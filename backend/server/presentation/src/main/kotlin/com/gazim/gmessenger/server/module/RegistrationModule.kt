package com.gazim.gmessenger.server.module

import com.gazim.gmessenger.server.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.database.model.AccountEntity
import com.gazim.gmessenger.server.database.model.LoginEntity
import com.gazim.gmessenger.server.database.model.PasswordEntity
import com.gazim.gmessenger.server.database.table.AccountTable
import com.gazim.gmessenger.server.model.IAccount

class RegistrationModule : IRegistrationModule {
    override suspend fun register(account: IAccount): Boolean =
        dbQuery {
            if (!AccountEntity.find { AccountTable.username eq account.username }.empty()) {
                return@dbQuery false
            }
            val accountEntity =
                AccountEntity.new {
                    nickname = account.nickname
                    username = account.username
                }
            LoginEntity.new {
                login = account.login
                this.account = accountEntity
            }
            PasswordEntity.new {
                password = account.password
                this.account = accountEntity
            }
            true
        }
}
