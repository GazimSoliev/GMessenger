package com.gazim.gmessenger.domain.factrory

import com.gazim.gmessenger.domain.api.GMessengerAPI
import com.gazim.gmessenger.domain.api.GMessengerAuthAPI

interface GMessengerAuthAPIFactory {
    operator fun invoke(): GMessengerAuthAPI
}

interface GMessengerAPIFactory {
    operator fun invoke(token: String): GMessengerAPI
}
