package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.model.AuthAPIConfig
import com.gazim.gmessenger.domain.model.AuthenticationForm
import com.gazim.gmessenger.domain.model.RegistrationForm

class GMessengerAuthSessionServiceImpl(
    private val gMessengerConnectionService: GMessengerConnectionService,
    private val gMessengerAuthService: GMessengerAuthService
) : GMessengerAuthSessionService {
    private var _config: AuthAPIConfig? = null
    private val config get() = _config ?: error("API config not set")

    override suspend fun register(registrationForm: RegistrationForm) = gMessengerAuthService.register(config, registrationForm)

    override suspend fun login(loginPasswordModel: AuthenticationForm) = gMessengerAuthService.login(config, loginPasswordModel)

    override suspend fun createAPI() {
        val server = gMessengerConnectionService.getCurrentServer()!!
        val config = AuthAPIConfig(server.host, server.isSecure)
        gMessengerAuthService.createAPI(config)
        _config = config
    }

    override suspend fun closeAPI() {
        val config = _config ?: return
        gMessengerAuthService.closeAPI(config)
        _config = null
    }
}