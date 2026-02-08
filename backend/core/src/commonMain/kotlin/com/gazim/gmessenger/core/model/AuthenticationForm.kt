package com.gazim.gmessenger.core.model

import kotlinx.serialization.Serializable

@Serializable
public data class AuthenticationForm(
    val login: String,
    val password: String,
)
