package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.GMessengerConnectionService
import kotlin.time.Duration

class PingUseCaseImpl(
    private val gMessengerConnectionService: GMessengerConnectionService,
) : PingUseCase {
    override suspend fun invoke(url: String): Duration = gMessengerConnectionService.ping(url)
}
