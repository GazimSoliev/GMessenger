package com.gazim.gmessenger.domain.usecase

class ValidateNickname : IValidateNickname {
    private val nicknameRegex = Regex("^.{0,64}$")

    override suspend fun invoke(nickname: String): Boolean = nicknameRegex matches nickname
}
