package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.route.notificationRoute
import com.gazim.gmessenger.server.domain.usecase.GetNotifications
import com.gazim.gmessenger.server.extensions.toAPI
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject

fun Route.notificationRoute() {
    val getNotifications by inject<GetNotifications>()
    webSocket(notificationRoute) {
        val user = getUser()
        val notifications = getNotifications(user)
        notifications.collect { sendSerialized(it.toAPI()) }
    }
}
