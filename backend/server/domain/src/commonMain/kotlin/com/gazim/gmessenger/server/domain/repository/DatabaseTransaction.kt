package com.gazim.gmessenger.server.domain.repository

interface DatabaseTransaction {
    // Ensures transactionality when changing data
    suspend fun <T> execute(block: suspend () -> T): T
}

suspend operator fun <T> DatabaseTransaction.invoke(block: suspend () -> T) = execute(block)
