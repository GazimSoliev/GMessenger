package com.gazim.gmessenger.core.model

import kotlinx.serialization.Serializable

@Serializable
public data class MessageForm(
    val message: String,
)
