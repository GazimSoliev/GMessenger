package com.gazim.gmessenger.domain.usecase

class ValidateLogin : IValidateLogin {
    private val loginRegex = Regex("^[a-zA-Z0-9]{8,32}$")

    override suspend fun invoke(login: String): Boolean = loginRegex matches login
}
