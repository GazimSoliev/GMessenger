package com.gazim.gmessenger.data.model

data class UserData(
    override val nickname: String,
    override val username: String,
) : IUserData
