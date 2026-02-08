package com.gazim.gmessenger.core.model

import kotlinx.serialization.Serializable

@Serializable
public data class ProfileForm(
    val nickname: String,
    val username: String,
)
