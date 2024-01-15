package com.gazim.gmessenger.backend.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("PrivateChat")
data class PrivateChatPresent(
    override val identifier: String,
    override val title: String,
    override val user: IUserPresent,
) : IPrivateChatPresent
