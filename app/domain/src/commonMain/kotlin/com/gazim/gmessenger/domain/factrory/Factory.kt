package com.gazim.gmessenger.domain.factrory

import com.gazim.gmessenger.domain.api.GMessengerAPI
import com.gazim.gmessenger.domain.api.GMessengerAuthAPI
import com.gazim.gmessenger.domain.model.APIConfig
import com.gazim.gmessenger.domain.model.AuthAPIConfig

interface GMessengerAuthAPIFactory {
    operator fun invoke(config: AuthAPIConfig): GMessengerAuthAPI
}

interface GMessengerAPIFactory {
    operator fun invoke(config: APIConfig): GMessengerAPI
}
