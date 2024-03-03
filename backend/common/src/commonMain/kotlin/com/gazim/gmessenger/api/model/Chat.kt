package com.gazim.gmessenger.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("Chat")
@Serializable
data class Chat(
    override val id: String,
    override val title: String,
) : IChat
