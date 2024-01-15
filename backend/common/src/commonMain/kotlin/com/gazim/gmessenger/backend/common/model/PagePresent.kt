package com.gazim.gmessenger.backend.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Page")
data class PagePresent<T>(
    override val next: Int?,
    override val previous: Int?,
    override val list: List<T>,
) : IPagePresent<T>
