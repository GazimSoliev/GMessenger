package com.gazim.gmessenger.data.factory

import com.gazim.gmessenger.data.api.GMessengerAuthAPIImpl
import com.gazim.gmessenger.domain.api.GMessengerAuthAPI
import com.gazim.gmessenger.domain.factrory.GMessengerAuthAPIFactory
import com.gazim.gmessenger.domain.model.AuthAPIConfig

class GMessengerAuthAPIFactoryImpl : GMessengerAuthAPIFactory {
    override fun invoke(config: AuthAPIConfig): GMessengerAuthAPI =
        GMessengerAuthAPIImpl(
            host = config.host,
            isSecure = config.isSecure,
        )
}
