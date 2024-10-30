package com.gazim.gmessenger.presentation.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
class PrivateChatUI(
    @Contextual
    override val identifier: Uuid,
    override val title: String,
    override val chatName: String,
    override val chatLink: String,
    @Contextual
    override val image: Uuid?,
    val user: UserUI,
) : IChatUI
