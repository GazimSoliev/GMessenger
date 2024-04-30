package com.gazim.gmessenger.api

fun GMessengerAPI(token: String): GMessengerAPI = GMessengerAPIImpl(token)

fun GMessengerAuthAPI(): GMessengerAuthAPI = GMessengerAuthAPIImpl()
