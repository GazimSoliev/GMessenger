package com.gazim.gmessenger.app.model

data class AccountUI(
    override val nickname: String,
    override val username: String,
    override val login: String,
    override val password: String,
) : IAccountUI
