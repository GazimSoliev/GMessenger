package com.gazim.gmessenger.domain.model

data class GMessengerServer(
    val host: String,
    val isSecure: Boolean,
    val title: String,
)
