package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.LoginEntity
import com.gazim.gmessenger.server.data.database.model.PasswordEntity
import com.gazim.gmessenger.server.data.database.model.TokenEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.database.table.LoginTable
import com.gazim.gmessenger.server.data.database.table.PasswordTable
import com.gazim.gmessenger.server.domain.model.AuthenticationForm
import com.gazim.gmessenger.server.domain.model.RegistrationForm
import com.gazim.gmessenger.server.domain.model.Token
import com.gazim.gmessenger.server.domain.repository.ILoginRegisterRepository
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

class LoginRegisterRepository : ILoginRegisterRepository {
    override suspend fun login(
        loginPassword: AuthenticationForm,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Token? =
        dbQuery {
            val accountEntity =
                AccountTable.innerJoin(LoginTable)
                    .innerJoin(PasswordTable).select {
                        (AccountTable.id eq LoginTable.idAccount)
                            .and(AccountTable.id eq PasswordTable.idAccount)
                            .and(LoginTable.login eq loginPassword.login)
                            .and(PasswordTable.password eq loginPassword.password)
                    }.singleOrNull()?.let(AccountEntity.Companion::wrapRow)
                    ?: return@dbQuery null
            val tokenEntity =
                TokenEntity.new {
                    this.createdAt = createdAt
                    this.expiredAt = expiredAt
                    this.account = accountEntity
                }
            Token(
                id = tokenEntity.id.value,
                expiredAt = tokenEntity.expiredAt,
            )
        }

    override suspend fun register(account: RegistrationForm): Boolean =
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
