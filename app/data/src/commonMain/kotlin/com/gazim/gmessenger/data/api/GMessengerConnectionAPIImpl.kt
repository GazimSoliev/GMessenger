@file:Suppress("SpellCheckingInspection")

package com.gazim.gmessenger.data.api

import com.gazim.gmessenger.data.model.toDomain
import com.gazim.gmessenger.domain.api.GMessengerConnectionAPI
import com.gazim.gmessenger.domain.model.GMessengerServer
import kotlin.time.Duration
import com.gazim.gmessenger.core.GMessengerAPIConnection as GMCAPI
import com.gazim.gmessenger.core.model.GMessengerServer as GMessengerServerAPI

class GMessengerConnectionAPIImpl : GMessengerConnectionAPI {
    private val gMessengerConnectionAPI = GMCAPI()
    override val availableServers: List<GMessengerServer>
        get() = gMessengerConnectionAPI.availableServers.map(GMessengerServerAPI::toDomain)

    override suspend fun ping(
        host: String,
        isSecure: Boolean,
    ): Duration = gMessengerConnectionAPI.ping(host, isSecure)
}
