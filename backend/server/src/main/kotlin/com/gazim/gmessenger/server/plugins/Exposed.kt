package com.gazim.gmessenger.server.plugins

import com.gazim.gmessenger.server.database.GMessengerDatabase
import io.ktor.server.application.*

fun Application.configureExposed() = GMessengerDatabase.init()
