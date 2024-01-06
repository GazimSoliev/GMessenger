package com.gazim.gmessenger.app.model

data class UserUI(
    override val nickname: String,
    override val username: String,
) : IUserUI
