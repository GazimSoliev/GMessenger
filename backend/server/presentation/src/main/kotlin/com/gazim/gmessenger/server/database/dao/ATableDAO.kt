package com.gazim.gmessenger.server.database.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

abstract class ATableDAO<T : IntEntity>(private val entity: IntEntityClass<T>) : ITableDAO<T> {
    private suspend fun <T> query(block: suspend Transaction.() -> T): T =
        newSuspendedTransaction(context = Dispatchers.IO, statement = block)

    override suspend fun getSize(): Long = query { entity.count() }

    override suspend fun getAll(
        n: Int,
        offset: Long,
    ): List<T> =
        query {
            entity.all().limit(n, offset).toList()
        }

    override suspend fun get(id: Int): T = query { entity[id] }

    override suspend fun insert(value: T): T {
        entity.new {
        }
        TODO()
    }

    override suspend fun update(value: T): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(value: T): Boolean {
        TODO("Not yet implemented")
    }
}
