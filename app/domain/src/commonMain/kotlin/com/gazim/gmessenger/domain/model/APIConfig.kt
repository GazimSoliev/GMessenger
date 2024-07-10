package com.gazim.gmessenger.domain.model

data class APIConfig(
    val host: String,
    val isSecure: Boolean,
    val token: String,
)