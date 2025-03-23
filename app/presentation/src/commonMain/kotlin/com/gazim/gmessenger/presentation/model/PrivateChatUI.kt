package com.gazim.gmessenger.presentation.model

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
class PrivateChatUI(
    override val identifier: Uuid,
    override val title: String,
    override val chatName: String,
    override val chatLink: String,
    override val image: Uuid?,
    val user: UserUI,
) : IChatUI
