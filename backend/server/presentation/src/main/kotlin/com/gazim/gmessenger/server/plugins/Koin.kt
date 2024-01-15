package com.gazim.gmessenger.server.plugins

import com.gazim.gmessenger.server.di.module
import com.gazim.gmessenger.server.di.utils
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(module, utils)
    }
}
