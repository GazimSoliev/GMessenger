package com.gazim.gmessenger.data.factory

import com.gazim.gmessenger.data.api.GMessengerAPIImpl
import com.gazim.gmessenger.domain.api.GMessengerAPI
import com.gazim.gmessenger.domain.factrory.GMessengerAPIFactory
import com.gazim.gmessenger.domain.model.APIConfig

class GMessengerAPIFactoryImpl : GMessengerAPIFactory {
    override fun invoke(config: APIConfig): GMessengerAPI = GMessengerAPIImpl(
        host = config.host,
        isSecure = config.isSecure,
        token = config.token
    )
}
