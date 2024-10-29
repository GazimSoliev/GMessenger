package com.gazim.gmessenger.server.domain.model

class MessagePage(
    override val data: List<Message>,
    override val next: MessagePageKey? = null,
    override val prev: MessagePageKey? = null,
) : Page<MessagePageKey, List<Message>>
