package com.gazim.gmessenger.data.repository

import com.gazim.gmessenger.client.pc.domain.model.IChatWebSocketModel
import com.gazim.gmessenger.client.pc.domain.model.IMessageModel
import com.gazim.gmessenger.client.pc.domain.model.ISentMessageModel
import com.gazim.gmessenger.client.pc.domain.repository.IChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ChatRepository(private val chatWebSocket: IChatWebSocketModel) : IChatRepository {
    override val messages: Flow<List<IMessageModel>> = chatWebSocket.messages

    override suspend fun sendMessage(msg: ISentMessageModel) = chatWebSocket.sendMessage(msg)

    override suspend fun openConnection() = chatWebSocket.openConnection()

    override suspend fun close() =
        withContext(Dispatchers.IO) {
            chatWebSocket.close()
        }

    override suspend fun getChatName(): String = chatWebSocket.chatName
}
