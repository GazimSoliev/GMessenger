package com.gazim.gmessenger.data.service

import com.gazim.gmessenger.domain.model.IChatWebSocketModel
import com.gazim.gmessenger.domain.model.IMessageModel
import com.gazim.gmessenger.domain.model.ISentMessageModel
import com.gazim.gmessenger.domain.service.IChatService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ChatService(private val chatWebSocket: IChatWebSocketModel) : IChatService {
    override val messages: Flow<IMessageModel> = chatWebSocket.messages

    override suspend fun sendMessage(msg: ISentMessageModel) = chatWebSocket.sendMessage(msg)

    override suspend fun openConnection() = chatWebSocket.openConnection()

    override suspend fun close() =
        withContext(Dispatchers.IO) {
            chatWebSocket.close()
        }

    override suspend fun getChatName(): String = chatWebSocket.chatName
}
