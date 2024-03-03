package com.gazim.gmessenger.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("PrivateChat")
data class PrivateChat(
    override val id: String,
    override val title: String,
    val user: User,
) : IChat
