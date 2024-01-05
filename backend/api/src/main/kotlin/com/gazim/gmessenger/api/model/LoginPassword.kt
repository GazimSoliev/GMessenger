package com.gazim.gmessenger.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("LoginPassword")
data class LoginPassword(
    override val login: String,
    override val password: String,
) : ILoginPassword
