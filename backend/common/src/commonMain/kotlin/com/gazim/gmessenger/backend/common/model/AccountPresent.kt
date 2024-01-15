package com.gazim.gmessenger.backend.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Account")
data class AccountPresent(
    override val nickname: String,
    override val username: String,
    override val login: String,
    override val password: String,
) : IAccountPresent
