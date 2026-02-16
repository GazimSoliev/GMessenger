@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public interface IChat {
    public val id: Uuid
    public val title: String
    public val lastMessage: Message?
}
