package com.gazim.gmessenger.presentation.model

data class ServerInfoUI(
    val server: String,
    val url: String,
    val ping: String,
    val selected: Boolean
)