package com.gazim.gmessenger.domain.model

data class UserModel(
    override val id: String,
    override val nickname: String,
    override val username: String,
    override val photo: Image?,
) : IUserModel
