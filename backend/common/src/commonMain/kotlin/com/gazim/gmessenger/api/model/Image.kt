package com.gazim.gmessenger.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: String,
    val type: String,
)
