package com.gazim.gmessenger.domain.model

data class UserModel(
    override val nickname: String,
    override val username: String,
) : IUserModel
