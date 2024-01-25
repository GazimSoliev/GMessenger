package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.backend.common.route.notificationRoute
import com.gazim.gmessenger.server.domain.usecase.IGetNotifications
import com.gazim.gmessenger.server.extensions.toPresent
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject

fun Route.notificationRoute() {
    val getNotifications by inject<IGetNotifications>()
    webSocket(notificationRoute) {
        val user = getUser()
        val notifications = getNotifications(user)
        notifications.collect { sendSerialized(it.toPresent()) }
    }
}
