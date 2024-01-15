package com.gazim.gmessenger.server.database.dao

interface ITableDAO<T> {
    suspend fun getSize(): Long

    suspend fun getAll(
        n: Int,
        offset: Long = 0L,
    ): List<T>

    suspend fun get(id: Int): T

    suspend fun insert(value: T): T

    suspend fun insert(vararg values: T): List<T?> = values.map { insert(it) }

    suspend fun update(value: T): Boolean

    suspend fun update(vararg values: T): Boolean = values.minOfOrNull { update(it) } ?: false

    suspend fun delete(value: T): Boolean

    suspend fun delete(vararg values: T): Boolean = values.minOfOrNull { delete(it) } ?: false
}
