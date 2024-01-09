package com.gazim.gmessenger.backend.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Page")
data class Page<T>(
    override val next: Int?,
    override val previous: Int?,
    override val list: List<T>,
) : IPage<T>
