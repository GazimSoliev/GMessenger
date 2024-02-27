package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.ISessionService

class PassAuthUseCase(private val sessionRepository: ISessionService) : IPassAuthUseCase {
    override fun invoke(): Boolean = sessionRepository.token.isNotEmpty()
}
