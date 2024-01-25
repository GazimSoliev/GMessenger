package com.gazim.gmessenger.server.domain.model

data class User(
    override val nickname: String,
    override val username: String,
) : IUser
