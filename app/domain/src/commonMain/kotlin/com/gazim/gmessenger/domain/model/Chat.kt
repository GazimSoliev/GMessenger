@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Chat(
    override val id: Uuid,
    override val title: String,
    override val lastMessage: IMessage?,
) : IChat
