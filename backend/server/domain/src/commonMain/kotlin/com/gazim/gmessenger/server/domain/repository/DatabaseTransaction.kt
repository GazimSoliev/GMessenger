package com.gazim.gmessenger.server.domain.repository

public interface DatabaseTransaction {
    // Ensures transactionality when changing data
    public suspend fun <T> execute(block: suspend () -> T): T
}

public suspend operator fun <T> DatabaseTransaction.invoke(block: suspend () -> T): T = execute(block)
