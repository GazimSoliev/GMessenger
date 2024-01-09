package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.route.notificationRoute
import com.gazim.gmessenger.server.di.notificationModule
import io.ktor.server.routing.*
import io.ktor.server.websocket.*

fun Route.notificationRoute() {
    webSocket(notificationRoute) {
        val user = getUser()
        val notifications = notificationModule.getNotifications(user)
        notifications.collect(::sendSerialized)
    }
}
