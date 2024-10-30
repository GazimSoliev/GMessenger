package com.gazim.gmessenger.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class PrivateChat(
    override val id: Uuid,
    override val title: String,
    val user: User,
) : IChat
