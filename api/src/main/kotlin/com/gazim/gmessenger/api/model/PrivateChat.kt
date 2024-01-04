package com.gazim.gmessenger.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("PrivateChat")
data class PrivateChat(
    override val identifier: String,
    override val title: String,
    override val user: IUser,
) : IPrivateChat
