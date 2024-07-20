package com.gazim.gmessenger.presentation.model

data class ServerInfoUI(
    val server: String,
    val host: String,
    val isSecure: Boolean,
    val ping: String,
    val selected: Boolean,
    val id: Int = 0
)