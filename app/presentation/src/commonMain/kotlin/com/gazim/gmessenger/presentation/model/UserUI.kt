package com.gazim.gmessenger.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class UserUI(
    val id: String = "",
    val nickname: String = "",
    val username: String = "",
    val photo: ImageUI? = null,
)
