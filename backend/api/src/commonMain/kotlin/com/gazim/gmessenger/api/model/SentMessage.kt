package com.gazim.gmessenger.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("SentMessage")
data class SentMessage(
    override val message: String,
) : ISentMessage
