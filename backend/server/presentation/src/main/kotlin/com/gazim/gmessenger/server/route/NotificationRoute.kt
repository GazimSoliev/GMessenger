package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.api.route.NotificationsRoute
import com.gazim.gmessenger.server.domain.usecase.GetNotifications
import com.gazim.gmessenger.server.extensions.toAPI
import com.gazim.gmessenger.server.extensions.webSocket
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject

fun Route.notificationRoute() {
    val getNotifications by inject<GetNotifications>()
    webSocket<NotificationsRoute> {
        val user = getUser()
        val notifications = getNotifications(user)
        notifications.collect { sendSerialized(it.toAPI()) }
    }
}
