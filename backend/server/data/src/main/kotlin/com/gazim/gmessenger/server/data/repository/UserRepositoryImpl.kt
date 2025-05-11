package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.database.table.LoginTable
import com.gazim.gmessenger.server.data.database.table.PasswordTable
import com.gazim.gmessenger.server.domain.repository.UserRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.and
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toKotlinUuid

@OptIn(ExperimentalUuidApi::class)
class UserRepositoryImpl : UserRepository {
    override suspend fun insertAndGetId(
        nickname: String,
        username: String,
        createdAt: Instant
    ): Uuid {
        val accountEntity = AccountEntity.new {
            this.nickname = nickname
            this.username = username
            this.createdAt = createdAt.toLocalDateTime(TimeZone.UTC)
        }
        return accountEntity.id.value.toKotlinUuid()
    }

    override suspend fun checkUserExist(username: String) =
        AccountEntity.find { AccountTable.username eq username }.empty()

    override suspend fun findUserByLoginAndPassword(
        login: ByteArray,
        password: ByteArray
    ) = AccountTable
        .innerJoin(LoginTable)
        .innerJoin(PasswordTable)
        .select(AccountTable.id)
        .where {
            (AccountTable.id eq LoginTable.idAccount) and
                    (AccountTable.id eq PasswordTable.idAccount) and
                    (LoginTable.login eq login) and
                    (PasswordTable.password eq password)
        }.singleOrNull()
        ?.let { resultRow ->
            resultRow[AccountTable.id].value.toKotlinUuid()
        }
}