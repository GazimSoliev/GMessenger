package com.gazim.gmessenger.server.module

import com.gazim.gmessenger.server.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.database.model.AccountEntity
import com.gazim.gmessenger.server.database.table.AccountTable
import com.gazim.gmessenger.server.database.table.LoginTable
import com.gazim.gmessenger.server.database.table.PasswordTable
import com.gazim.gmessenger.server.model.ILoginPassword
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select

class LoginModule : ILoginModule {
    override suspend fun login(loginPassword: ILoginPassword): Int? =
        dbQuery {
            AccountTable.innerJoin(LoginTable)
                .innerJoin(PasswordTable).select {
                    (AccountTable.id eq LoginTable.idAccount)
                        .and(AccountTable.id eq PasswordTable.idAccount)
                        .and(LoginTable.login eq loginPassword.login)
                        .and(PasswordTable.password eq loginPassword.password)
                }.singleOrNull()?.let(AccountEntity.Companion::wrapRow)?.id?.value
        }
}
