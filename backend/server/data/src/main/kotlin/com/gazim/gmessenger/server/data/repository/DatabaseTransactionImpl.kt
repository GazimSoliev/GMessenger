package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase
import com.gazim.gmessenger.server.domain.repository.DatabaseTransaction

class DatabaseTransactionImpl : DatabaseTransaction {
    override suspend fun <T> execute(block: suspend () -> T) =
        GMessengerDatabase.dbQuery { block() }
}