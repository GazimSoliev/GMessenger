package com.gazim.gmessenger.server.di

import com.gazim.gmessenger.server.module.*
import com.gazim.gmessenger.server.utils.ISecurityUtils
import io.ktor.server.application.*
import org.koin.ktor.ext.get

// val userModule by inject<IUserModule>(IUserModule::class.java)
// val notificationModule by inject<INotificationModule>(IUserModule::class.java)
// val loginModule by inject<ILoginModule>(ILoginModule::class.java)
// val chatModule by inject<IChatModule>(IChatModule::class.java)
// val messageModule by inject<IMessageModule>(IMessageModule::class.java)
// val tokenModule by inject<ITokenModule>(ITokenModule::class.java)
// val registrationModule by inject<IRegistrationModule>(IRegistrationModule::class.java)
// val securityUtils by inject<ISecurityUtils>(ISecurityUtils::class.java)

private lateinit var getUserModule: () -> IUserModule
val userModule by lazy { getUserModule() }

private lateinit var getNotificationModule: () -> INotificationModule
val notificationModule by lazy { getNotificationModule() }

private lateinit var getLoginModule: () -> ILoginModule
val loginModule by lazy { getLoginModule() }

private lateinit var getChatModule: () -> IChatModule
val chatModule by lazy { getChatModule() }

private lateinit var getMessageModule: () -> IMessageModule
val messageModule by lazy { getMessageModule() }

private lateinit var getTokenModule: () -> ITokenModule
val tokenModule by lazy { getTokenModule() }

private lateinit var getRegistrationModule: () -> IRegistrationModule
val registrationModule by lazy { getRegistrationModule() }

private lateinit var getSecurityUtils: () -> ISecurityUtils
val securityUtils by lazy { getSecurityUtils() }

fun Application.configureInjections() {
    getUserModule = { get() }
    getNotificationModule = { get() }
    getLoginModule = { get() }
    getChatModule = { get() }
    getMessageModule = { get() }
    getTokenModule = { get() }
    getRegistrationModule = { get() }
    getSecurityUtils = { get() }
}
