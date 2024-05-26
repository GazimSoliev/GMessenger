package com.gazim.gmessenger.api

fun GMessengerAPI(token: String, urlServer: String): GMessengerAPI = GMessengerAPIImpl(token, urlServer)

fun GMessengerAuthAPI(urlServer: String): GMessengerAuthAPI = GMessengerAuthAPIImpl(urlServer)

fun GMessengerAPIConnection(): GMessengerAPIConnection = GMessengerAPIConnectionImpl()
