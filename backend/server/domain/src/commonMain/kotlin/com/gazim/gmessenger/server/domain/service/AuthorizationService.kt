package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.IAccount
import com.gazim.gmessenger.server.domain.model.ILoginPassword
import com.gazim.gmessenger.server.domain.repository.ILoginRegisterRepository
import kotlinx.datetime.LocalDateTime

class AuthorizationService(
    private val loginPasswordRepository: ILoginRegisterRepository,
) : IAuthorizationService {
    override suspend fun login(
        loginPassword: ILoginPassword,
        createdAt: LocalDateTime,
        expiredAt: LocalDateTime,
    ): Int? = loginPasswordRepository.login(loginPassword, createdAt, expiredAt)

    override suspend fun register(account: IAccount): Boolean = loginPasswordRepository.register(account)
}
