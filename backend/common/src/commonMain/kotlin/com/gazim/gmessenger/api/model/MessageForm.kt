package com.gazim.gmessenger.api.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageForm(
    val message: String,
)
