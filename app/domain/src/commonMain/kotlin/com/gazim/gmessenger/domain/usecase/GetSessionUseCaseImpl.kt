package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.SessionService

class GetSessionUseCaseImpl(private val sessionService: SessionService) : GetSessionUseCase {
    override fun invoke(): String? = sessionService.currentSession()
}
