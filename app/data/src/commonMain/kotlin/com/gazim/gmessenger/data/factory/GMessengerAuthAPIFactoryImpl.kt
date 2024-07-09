package com.gazim.gmessenger.data.factory

import com.gazim.gmessenger.data.api.GMessengerAuthAPIImpl
import com.gazim.gmessenger.domain.api.GMessengerAuthAPI
import com.gazim.gmessenger.domain.factrory.GMessengerAuthAPIFactory

class GMessengerAuthAPIFactoryImpl : GMessengerAuthAPIFactory {
    override fun invoke(): GMessengerAuthAPI = GMessengerAuthAPIImpl()
}
