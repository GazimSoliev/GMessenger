package com.gazim.gmessenger.backend.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("LoginPassword")
data class LoginPassword(
    override val login: String,
    override val password: String,
) : ILoginPassword
