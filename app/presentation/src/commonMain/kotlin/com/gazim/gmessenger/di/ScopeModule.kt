package com.gazim.gmessenger.di

import com.gazim.gmessenger.data.service.ChatService
import com.gazim.gmessenger.data.service.ChatSessionService
import com.gazim.gmessenger.data.service.GMessengerService
import com.gazim.gmessenger.domain.service.IChatService
import com.gazim.gmessenger.domain.service.IChatSessionService
import com.gazim.gmessenger.domain.service.IGMessengerService
import kotlinx.coroutines.runBlocking
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module

val scopeModule =
    module {
        scope<AccountScope> {
            scoped<IGMessengerService> { GMessengerService(getCurrentToken()) }
            scopedOf(::ChatSessionService) bind IChatSessionService::class
//            scopeNotification()
//            scoped<INotificationRepository> { NotificationRepository(runBlocking { get<IGMessengerRepository>().getNotifications() }) }
        }
        scope<ChatScope> {
            scoped<IChatService> {
                val gMessengerRepository: IGMessengerService = get()
                val chatSessionRepository: IChatSessionService = get()
                ChatService(runBlocking { gMessengerRepository.getChat(chatSessionRepository.currentChat) })
            }
        }
    }

class AccountScope

class ChatScope

// expect fun ScopeDSL.scopeNotification()
