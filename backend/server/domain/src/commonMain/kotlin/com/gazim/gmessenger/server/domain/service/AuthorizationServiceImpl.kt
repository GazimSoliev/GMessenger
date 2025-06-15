package com.gazim.gmessenger.server.domain.service

import com.gazim.gmessenger.server.domain.model.AuthenticationForm
import com.gazim.gmessenger.server.domain.model.RegistrationForm
import com.gazim.gmessenger.server.domain.model.Token
import com.gazim.gmessenger.server.domain.repository.*
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.days
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class AuthorizationServiceImpl(
    private val shA256Service: SHA256Service,
    private val transaction: DatabaseTransaction,
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository,
    private val passwordRepository: PasswordRepository,
    private val tokenRepository: TokenRepository,
) : AuthorizationService {
    override suspend fun login(loginPassword: AuthenticationForm): Token? {
        val login = shA256Service.encode(loginPassword.login.encodeToByteArray())
        val password = shA256Service.encode(loginPassword.password.encodeToByteArray())
        val createdAt = Clock.System.now()
        val expiredAt = createdAt + 7.days
        var token: Token? = null
        transaction {
            val userId =
                userRepository.findUserByLoginAndPassword(
                    login = login,
                    password = password,
                ) ?: return@transaction
            token =
                tokenRepository.insertAndGetToken(
                    createdAt = createdAt,
                    expiredAt = expiredAt,
                    userId = userId,
                )
        }
        return token
    }

    override suspend fun register(account: RegistrationForm): Boolean {
        val userExist =
            transaction {
                userRepository.checkUserExist(account.username)
            }
        if (!userExist) return false
        val login = shA256Service.encode(account.login.encodeToByteArray())
        val password = shA256Service.encode(account.password.encodeToByteArray())
        val createdAt = Clock.System.now()
        transaction {
            val userId =
                userRepository.insertAndGetId(
                    nickname = account.nickname,
                    username = account.username,
                    createdAt = createdAt,
                )
            loginRepository.insert(
                login = login,
                userId = userId,
                createdAt = createdAt,
            )
            passwordRepository.insert(
                password = password,
                userId = userId,
                createdAt = createdAt,
            )
        }
        return true
    }
}
