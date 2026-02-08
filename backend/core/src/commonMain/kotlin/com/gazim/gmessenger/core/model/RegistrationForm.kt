package com.gazim.gmessenger.core.model

import kotlinx.serialization.Serializable

@Serializable
public data class RegistrationForm(
    val nickname: String,
    val username: String,
    val login: String,
    val password: String,
)
