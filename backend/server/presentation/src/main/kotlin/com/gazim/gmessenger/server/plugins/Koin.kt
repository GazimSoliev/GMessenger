package com.gazim.gmessenger.server.plugins

import com.gazim.utils.com.gazim.gmessenger.server.di.configure
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        configure()
        slf4jLogger()
    }
}
