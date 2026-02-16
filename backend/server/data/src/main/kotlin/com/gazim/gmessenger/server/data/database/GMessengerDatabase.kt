package com.gazim.gmessenger.server.data.database

import com.gazim.gmessenger.server.data.database.table.*
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

public object GMessengerDatabase {
    public fun init() {
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
            SchemaUtils.create(ImageTable)
            SchemaUtils.create(ProfilePhotoTable)
        }
    }

    internal suspend fun <T> dbQuery(block: suspend Transaction.() -> T): T = suspendTransaction(statement = block)
}
