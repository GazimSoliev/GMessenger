package com.gazim.gmessenger.di

import com.gazim.gmessenger.app.service.INotificationService
import com.gazim.gmessenger.app.service.NotificationService
import com.gazim.gmessenger.data.repository.ChatRepository
import com.gazim.gmessenger.data.repository.ChatSessionRepository
import com.gazim.gmessenger.data.repository.GMessengerRepository
import com.gazim.gmessenger.data.repository.NotificationRepository
import com.gazim.gmessenger.domain.repository.IChatRepository
import com.gazim.gmessenger.domain.repository.IChatSessionRepository
import com.gazim.gmessenger.domain.repository.IGMessengerRepository
import com.gazim.gmessenger.domain.repository.INotificationRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module

val scopeModule =
    module {
        scope<AccountScope> {
            scoped<IGMessengerRepository> { GMessengerRepository(getCurrentToken()) }
            scopedOf(::ChatSessionRepository) bind IChatSessionRepository::class
            scopedOf(::NotificationService) bind INotificationService::class
            scoped<INotificationRepository> { NotificationRepository(runBlocking { get<IGMessengerRepository>().getNotifications() }) }
        }
        scope<ChatScope> {
            scoped<IChatRepository> {
                val gMessengerRepository: IGMessengerRepository = get()
                val chatSessionRepository: IChatSessionRepository = get()
                ChatRepository(runBlocking { gMessengerRepository.getChat(chatSessionRepository.currentChat) })
            }
        }
    }

class AccountScope

class ChatScope
