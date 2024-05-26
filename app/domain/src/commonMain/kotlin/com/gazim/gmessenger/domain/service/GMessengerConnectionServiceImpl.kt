package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.api.GMessengerConnectionAPI
import com.gazim.gmessenger.domain.model.GMessengerServer
import kotlin.time.Duration

class GMessengerConnectionServiceImpl(private val gMessengerConnectionAPI: GMessengerConnectionAPI) :
    GMessengerConnectionService {
    private val _servers = mutableListOf<GMessengerServer>()
    override val availableServers: List<GMessengerServer>
        get() = gMessengerConnectionAPI.availableServers + _servers

    override suspend fun ping(url: String): Duration = gMessengerConnectionAPI.ping(url)

    override suspend fun addServer(server: GMessengerServer) {
        _servers.add(server)
    }
}
