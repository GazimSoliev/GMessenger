package com.gazim.gmessenger.api.model

import kotlinx.serialization.Serializable

@Serializable
class MessagePage(
    override val data: List<Message>,
    override val next: MessagePageKey? = null,
    override val prev: MessagePageKey? = null,
) : Page<MessagePageKey, List<Message>>
