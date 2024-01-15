package com.gazim.gmessenger.backend.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Chat")
data class ChatPresent(
    override val identifier: String,
    override val title: String,
) : IChatPresent
