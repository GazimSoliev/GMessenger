package com.gazim.gmessenger.core.model

import kotlinx.serialization.Serializable

@Serializable
public data class MessagePage(
    override val data: List<Message>,
    override val next: MessagePageKey? = null,
    override val prev: MessagePageKey? = null,
) : Page<MessagePageKey, List<Message>>
