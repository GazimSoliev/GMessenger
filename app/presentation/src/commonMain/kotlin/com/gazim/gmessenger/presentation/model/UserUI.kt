package com.gazim.gmessenger.presentation.model

data class UserUI(
    override val id: String,
    override val nickname: String,
    override val username: String,
) : IUserUI
