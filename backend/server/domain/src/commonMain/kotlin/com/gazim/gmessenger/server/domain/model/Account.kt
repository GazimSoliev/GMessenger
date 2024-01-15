package com.gazim.gmessenger.server.domain.model

data class Account(
    override val nickname: String,
    override val username: String,
    override val login: String,
    override val password: String
): IAccount
