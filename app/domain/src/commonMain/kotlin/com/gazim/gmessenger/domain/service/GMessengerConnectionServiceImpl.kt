package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.api.GMessengerConnectionAPI
import com.gazim.gmessenger.domain.model.GMessengerServer
import kotlin.time.Duration

class GMessengerConnectionServiceImpl(private val gMessengerConnectionAPI: GMessengerConnectionAPI) :
    GMessengerConnectionService {
    override val availableServers: List<GMessengerServer>
        get() = gMessengerConnectionAPI.availableServers

    override suspend fun ping(url: String): Duration = gMessengerConnectionAPI.ping(url)
}
