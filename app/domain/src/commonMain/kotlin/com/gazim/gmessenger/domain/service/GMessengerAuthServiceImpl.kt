package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.api.GMessengerAuthAPI
import com.gazim.gmessenger.domain.factrory.GMessengerAuthAPIFactory
import com.gazim.gmessenger.domain.model.AuthAPIConfig
import com.gazim.gmessenger.domain.model.AuthenticationForm
import com.gazim.gmessenger.domain.model.RegistrationForm

class GMessengerAuthServiceImpl(
    private val gMessengerAuthAPIFactory: GMessengerAuthAPIFactory,
) : GMessengerAuthService {
    private val mapApi = mutableMapOf<AuthAPIConfig, GMessengerAuthAPI>()

    override suspend fun register(config: AuthAPIConfig, registrationForm: RegistrationForm) = getAPI(config).register(registrationForm)

    override suspend fun login(config: AuthAPIConfig, loginPasswordModel: AuthenticationForm) = getAPI(config).login(loginPasswordModel)

    override suspend fun createAPI(config: AuthAPIConfig) {
        mapApi[config] = gMessengerAuthAPIFactory(config)
    }

    override suspend fun closeAPI(config: AuthAPIConfig) {
        val api = mapApi.remove(config)
        checkNotNull(api)
//        api.close()
    }

    private fun getAPI(config: AuthAPIConfig) = mapApi.getValue(config)
}
