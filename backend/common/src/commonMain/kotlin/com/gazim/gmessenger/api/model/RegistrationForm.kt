package com.gazim.gmessenger.api.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationForm(
    val nickname: String,
    val username: String,
    val login: String,
    val password: String,
)
