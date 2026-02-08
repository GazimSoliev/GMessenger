package com.gazim.gmessenger.core.model

data class GMessengerServer(
    val host: String,
    val isSecure: Boolean,
    val title: String,
    val description: String,
)
