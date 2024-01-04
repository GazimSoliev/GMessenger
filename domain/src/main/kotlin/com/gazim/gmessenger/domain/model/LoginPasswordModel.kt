package com.gazim.gmessenger.domain.model

data class LoginPasswordModel(
    override val login: String,
    override val password: String,
) : ILoginPasswordModel
