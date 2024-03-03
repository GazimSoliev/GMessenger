package com.gazim.gmessenger.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Page<T>(
    val next: String?,
    val previous: String?,
    val list: List<T>,
)
