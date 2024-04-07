package com.gazim.gmessenger.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileForm(
    val nickname: String,
    val username: String,
)
