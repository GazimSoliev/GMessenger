package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.api.GMessengerConnectionAPI
import com.gazim.gmessenger.domain.model.GMessengerServer
import kotlin.time.Duration

class GMessengerConnectionServiceImpl(
    private val gMessengerConnectionAPI: GMessengerConnectionAPI,
) : GMessengerConnectionService {
    private var currentServer: GMessengerServer? = gMessengerConnectionAPI.availableServers.firstOrNull()
    private val _servers = mutableListOf<GMessengerServer>()
    override val availableServers: List<GMessengerServer>
        get() = gMessengerConnectionAPI.availableServers + _servers

    override suspend fun ping(
        host: String,
        isSecure: Boolean,
    ): Duration = gMessengerConnectionAPI.ping(host, isSecure)

    override fun getCurrentServer(): GMessengerServer? = currentServer

    override suspend fun addServer(server: GMessengerServer) {
        _servers.add(server)
    }

    override suspend fun applyServer(server: GMessengerServer) {
        currentServer = server
    }
}
