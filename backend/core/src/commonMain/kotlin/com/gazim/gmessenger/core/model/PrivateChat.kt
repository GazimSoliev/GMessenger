@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
@SerialName("PrivateChat")
public data class PrivateChat(
    override val id: Uuid,
    override val title: String,
    val user: User,
    override val lastMessage: Message?,
) : IChat
