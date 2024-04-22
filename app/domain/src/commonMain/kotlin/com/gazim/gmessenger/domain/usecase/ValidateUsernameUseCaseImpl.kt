package com.gazim.gmessenger.domain.usecase

class ValidateUsernameUseCaseImpl : ValidateUsernameUseCase {
    private val usernameRegex = Regex("^[a-zA-Z0-9]{4,32}$")

    override suspend fun invoke(username: String): Boolean = usernameRegex matches username
}
