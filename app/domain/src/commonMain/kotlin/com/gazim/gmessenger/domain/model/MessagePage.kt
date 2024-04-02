package com.gazim.gmessenger.domain.model

class MessagePage(
    override val data: List<IMessageModel>,
    override val next: MessagePageKey? = null,
    override val prev: MessagePageKey? = null,
) : Page<MessagePageKey, List<IMessageModel>>
