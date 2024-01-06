package com.gazim.gmessenger.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Chat")
data class Chat(
    override val identifier: String,
    override val title: String,
) : IChat
