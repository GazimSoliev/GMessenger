package com.gazim.gmessenger.domain.model

class MessagePage(
    override val data: List<IMessage>,
    override val next: MessagePageKey? = null,
    override val prev: MessagePageKey? = null,
) : Page<MessagePageKey, List<IMessage>>
