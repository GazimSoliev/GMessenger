package com.gazim.gmessenger.server.route

import com.gazim.gmessenger.server.plugins.jwtName
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Routing.generalRoute() {
    authenticate(jwtName) {
        chatsRoute()
        userRoute()
        createChatRoute()
        chatRoute()
        messagesRoute()
        findUserRoute()
        notificationRoute()
    }
    registrationRoute()
    loginRoute()
}
