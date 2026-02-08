@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@SerialName("Chat")
@Serializable
public data class Chat(
    override val id: Uuid,
    override val title: String,
    override val lastMessage: Message?,
) : IChat
