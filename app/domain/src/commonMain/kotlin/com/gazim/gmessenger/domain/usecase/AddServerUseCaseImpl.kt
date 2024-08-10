package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.GMessengerServer
import com.gazim.gmessenger.domain.service.GMessengerConnectionService

class AddServerUseCaseImpl(
    private val gMessengerConnectionService: GMessengerConnectionService,
) : AddServerUseCase {
    override suspend fun invoke(server: GMessengerServer) = gMessengerConnectionService.addServer(server)
}
