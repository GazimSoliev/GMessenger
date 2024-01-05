package com.gazim.gmessenger.data.model

data class LoginPasswordData(
    override val login: String,
    override val password: String,
) : ILoginPasswordData
