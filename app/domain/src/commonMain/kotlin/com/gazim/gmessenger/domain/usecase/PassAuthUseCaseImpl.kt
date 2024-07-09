package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.SessionService

class PassAuthUseCaseImpl(
    private val sessionRepository: SessionService,
) : PassAuthUseCase {
    override fun invoke(): Boolean = sessionRepository.currentToken()?.isNotEmpty() != null
}
