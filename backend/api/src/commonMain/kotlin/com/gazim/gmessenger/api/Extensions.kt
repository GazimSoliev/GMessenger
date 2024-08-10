package com.gazim.gmessenger.api

fun GMessengerAPI(
    host: String,
    isSecure: Boolean,
    token: String,
): GMessengerAPI =
    GMessengerAPIImpl(
        host = host,
        isSecure = isSecure,
        token = token,
    )

fun GMessengerAuthAPI(
    host: String,
    isSecure: Boolean,
): GMessengerAuthAPI =
    GMessengerAuthAPIImpl(
        host = host,
        isSecure = isSecure,
    )

fun GMessengerAPIConnection(): GMessengerAPIConnection = GMessengerAPIConnectionImpl()
