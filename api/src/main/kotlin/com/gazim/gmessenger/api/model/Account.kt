package com.gazim.gmessenger.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Account")
data class Account(
    override val nickname: String,
    override val username: String,
    override val login: String,
    override val password: String,
) : IAccount
