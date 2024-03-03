package com.gazim.gmessenger.api.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationForm(
    val login: String,
    val password: String,
)
