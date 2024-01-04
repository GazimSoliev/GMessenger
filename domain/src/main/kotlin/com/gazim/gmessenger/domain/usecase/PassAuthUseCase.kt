package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.repository.ISessionRepository

class PassAuthUseCase(private val sessionRepository: ISessionRepository) : IPassAuthUseCase {
    override fun invoke(): Boolean = sessionRepository.token.isNotEmpty()
}
