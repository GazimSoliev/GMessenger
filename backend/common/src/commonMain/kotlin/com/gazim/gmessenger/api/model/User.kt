package com.gazim.gmessenger.api.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val nickname: String,
    val username: String,
)
