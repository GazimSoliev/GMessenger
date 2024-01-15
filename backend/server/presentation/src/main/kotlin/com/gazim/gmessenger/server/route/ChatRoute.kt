package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.model.IChatPresent
import com.gazim.gmessenger.backend.common.model.IMessagePresent
import com.gazim.gmessenger.backend.common.model.ISentMessagePresent
import com.gazim.gmessenger.backend.common.model.IUserPresent
import com.gazim.gmessenger.backend.common.route.chatRoute
import com.gazim.gmessenger.server.di.chatModule
import com.gazim.gmessenger.server.di.messageModule
import com.gazim.gmessenger.server.di.notificationModule
import com.gazim.gmessenger.server.domain.usecase.IGetChatUseCase
import com.gazim.gmessenger.server.domain.usecase.IGetMessagesUseCase
import com.gazim.gmessenger.server.domain.usecase.ISendMessageUseCase
import com.gazim.gmessenger.server.extensions.toMessage
import com.gazim.gmessenger.server.extensions.toNotificationMessage
import io.ktor.serialization.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject

fun Route.chatRoute() {
    val listFlows = HashMap<IChatPresent, Pair<List<IUserPresent>, MutableStateFlow<List<IMessagePresent>>>>()
    val sendMessageUseCase by inject<ISendMessageUseCase>()
    val getMessagesUseCase by inject<IGetMessagesUseCase>()
    val getChatUseCase by inject<IGetChatUseCase>()
    webSocket(chatRoute) {
        val user = getUser()
        val chat =
            call.parameters["id"]?.toInt()
                ?.let { chatModule.getChat(user, it) } ?: return@webSocket println("Can't find chat")
        val getLastMessages = suspend { messageModule.getMessages(chat, 0).list }
        val pair =
            listFlows[chat] ?: (
                chatModule.getChatMembers(user, chat) to
                    MutableStateFlow(
                        getLastMessages(),
                    )
            ).also { listFlows[chat] = it }
        val chatFlow = pair.second
        launch(Dispatchers.IO) {
            chatFlow.collectLatest(::sendSerialized)
        }
        val members = pair.first.minus(user)
        for (frame in incoming) {
            val message = converter?.deserialize<ISentMessagePresent>(frame)?.toMessage(user) ?: continue
            messageModule.sendMessage(chat, message)
            members.forEach {
                notificationModule.sendNotification(it, message.toNotificationMessage(chat.title))
            }
            chatFlow.emit(getLastMessages())
        }
    }
}
