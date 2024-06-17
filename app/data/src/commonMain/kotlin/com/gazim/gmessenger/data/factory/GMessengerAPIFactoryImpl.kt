package com.gazim.gmessenger.data.factory

import com.gazim.gmessenger.data.api.GMessengerAPIImpl
import com.gazim.gmessenger.domain.api.GMessengerAPI
import com.gazim.gmessenger.domain.factrory.GMessengerAPIFactory

class GMessengerAPIFactoryImpl : GMessengerAPIFactory {
    override fun invoke(token: String): GMessengerAPI = GMessengerAPIImpl(token)
}
