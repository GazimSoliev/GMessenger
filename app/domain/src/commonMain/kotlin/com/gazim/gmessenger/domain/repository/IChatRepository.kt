package com.gazim.gmessenger.domain.repository

import com.gazim.gmessenger.domain.model.IMessageModel
import com.gazim.gmessenger.domain.model.ISentMessageModel
import kotlinx.coroutines.flow.Flow

interface IChatRepository {
    val messages: Flow<List<IMessageModel>>

    suspend fun sendMessage(msg: ISentMessageModel)

    suspend fun openConnection()

    suspend fun close()

    suspend fun getChatName(): String
}
