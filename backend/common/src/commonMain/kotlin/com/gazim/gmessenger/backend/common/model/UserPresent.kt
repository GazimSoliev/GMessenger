package com.gazim.gmessenger.backend.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("User")
data class UserPresent(
    override val nickname: String,
    override val username: String,
) : IUserPresent
