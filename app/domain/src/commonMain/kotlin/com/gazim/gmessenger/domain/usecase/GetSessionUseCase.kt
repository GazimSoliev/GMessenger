package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.ISessionService

class GetSessionUseCase(private val sessionService: ISessionService) : IGetSessionUseCase {
    override fun invoke(): String? = sessionService.currentSession()
}
