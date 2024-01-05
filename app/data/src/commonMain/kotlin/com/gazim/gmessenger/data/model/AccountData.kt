package com.gazim.gmessenger.data.model

data class AccountData(
    override val nickname: String,
    override val username: String,
    override val login: String,
    override val password: String,
) : IAccountData
