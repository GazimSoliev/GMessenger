package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.GMessengerServer
import com.gazim.gmessenger.domain.service.GMessengerAuthSessionService
import com.gazim.gmessenger.domain.service.GMessengerConnectionService

class SelectServerUseCaseImpl(
    private val gMessengerConnectionService: GMessengerConnectionService,
    private val gMessengerAuthSessionService: GMessengerAuthSessionService,
) : SelectServerUseCase {
    override suspend fun invoke(server: GMessengerServer) {
        gMessengerAuthSessionService.closeAPI()
        gMessengerConnectionService.applyServer(server)
        gMessengerAuthSessionService.createAPI()
    }
}
