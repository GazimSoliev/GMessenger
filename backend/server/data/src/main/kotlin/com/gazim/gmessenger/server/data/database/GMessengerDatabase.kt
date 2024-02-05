package com.gazim.gmessenger.server.data.database

import com.gazim.gmessenger.server.data.database.table.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object GMessengerDatabase {
    fun init() {
        val driverClassName = "org.mariadb.jdbc.Driver"
        val jdbcURL = "jdbc:mariadb://localhost:3306/gmessenger_db"
        val database =
            Database.connect(jdbcURL, driverClassName, user = "gmessenger_user", password = "gmessenger_user_password")
        transaction(database) {
            SchemaUtils.create(AccountTable)
            SchemaUtils.create(LoginTable)
            SchemaUtils.create(PasswordTable)
            SchemaUtils.create(TokenTable)
            SchemaUtils.create(ChatTable)
            SchemaUtils.create(MessageTable)
            SchemaUtils.create(ChatAccountTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend Transaction.() -> T): T = newSuspendedTransaction(context = Dispatchers.IO, statement = block)
}
