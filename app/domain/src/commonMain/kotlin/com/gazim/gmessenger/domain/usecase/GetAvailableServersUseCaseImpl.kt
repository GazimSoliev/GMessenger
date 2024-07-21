package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.GMessengerServer
import com.gazim.gmessenger.domain.service.GMessengerConnectionService

class GetAvailableServersUseCaseImpl(
    private val gMessengerConnectionAPI: GMessengerConnectionService,
) : GetAvailableServersUseCase {
    override suspend fun invoke(): List<GMessengerServer> = gMessengerConnectionAPI.availableServers
}
