@file:Suppress("INFERRED_TYPE_VARIABLE_INTO_EMPTY_INTERSECTION_WARNING")

package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.domain.model.AuthenticationForm
import com.gazim.gmessenger.server.domain.model.RegistrationForm
import com.gazim.gmessenger.server.domain.model.Token
import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.LoginEntity
import com.gazim.gmessenger.server.data.database.model.PasswordEntity
import com.gazim.gmessenger.server.data.database.model.TokenEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.database.table.LoginTable
import com.gazim.gmessenger.server.data.database.table.PasswordTable
import com.gazim.gmessenger.server.domain.extensions.nowInUTC
import com.gazim.gmessenger.server.domain.repository.ILoginRegisterRepository
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import java.security.MessageDigest
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.toKotlinUuid

@OptIn(ExperimentalUuidApi::class)
class LoginRegisterRepository : ILoginRegisterRepository {
    override suspend fun login(
        loginPassword: AuthenticationForm,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Token? =
        dbQuery {
            val sha256 = MessageDigest.getInstance("SHA-256")
            val login = sha256.digest(loginPassword.login.encodeToByteArray())
            val password = sha256.digest(loginPassword.password.encodeToByteArray())
            val accountEntity =
                AccountTable
                    .innerJoin(LoginTable)
                    .innerJoin(PasswordTable)
                    .selectAll()
                    .where {
                        (AccountTable.id eq LoginTable.idAccount) and
                            (AccountTable.id eq PasswordTable.idAccount) and
                            (LoginTable.login eq login) and
                            (PasswordTable.password eq password)
                    }.singleOrNull()
                    ?.let(AccountEntity.Companion::wrapRow)
                    ?: return@dbQuery null
            val tokenEntity =
                TokenEntity.new {
                    this.createdAt = createdAt
                    this.expiredAt = expiredAt
                    this.account = accountEntity
                }
            Token(
                id = tokenEntity.id.value.toKotlinUuid(),
                expiredAt = tokenEntity.expiredAt,
            )
        }

    override suspend fun register(account: RegistrationForm): Boolean =
        dbQuery {
            val sha256 = MessageDigest.getInstance("SHA-256")
            val login = sha256.digest(account.login.encodeToByteArray())
            val password = sha256.digest(account.password.encodeToByteArray())
            if (!AccountEntity.find { AccountTable.username eq account.username }.empty()) {
                return@dbQuery false
            }
            val accountEntity =
                AccountEntity.new {
                    nickname = account.nickname
                    username = account.username
                    createdAt = nowInUTC()
                }
            LoginEntity.new {
                this.login = login
                this.account = accountEntity
                createdAt = nowInUTC()
            }
            PasswordEntity.new {
                this.password = password
                this.account = accountEntity
                createdAt = nowInUTC()
            }
            true
        }
}
