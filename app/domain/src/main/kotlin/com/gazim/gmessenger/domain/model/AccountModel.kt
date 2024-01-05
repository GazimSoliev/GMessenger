package com.gazim.gmessenger.domain.model

data class AccountModel(
    override val nickname: String,
    override val username: String,
    override val login: String,
    override val password: String,
) : IAccountModel
