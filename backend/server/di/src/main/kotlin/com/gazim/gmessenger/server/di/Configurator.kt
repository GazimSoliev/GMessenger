package com.gazim.utils.com.gazim.gmessenger.server.di

import com.gazim.gmessenger.server.data.database.GMessengerDatabase
import org.koin.core.KoinApplication

fun KoinApplication.configure() {
    GMessengerDatabase.init()
    modules(repositoryModule, serviceModule, useCaseModule)
}
