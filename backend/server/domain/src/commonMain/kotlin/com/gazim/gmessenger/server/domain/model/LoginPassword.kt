package com.gazim.gmessenger.server.domain.model

data class LoginPassword(
    override val login: String,
    override val password: String,
) : ILoginPassword
