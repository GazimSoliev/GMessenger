package com.gazim.gmessenger.core.model

data class MyMessagePage(
    override val data: List<IMessage>,
    override val next: MessagePageKey? = null,
    override val prev: MessagePageKey? = null,
) : Page<MessagePageKey, List<IMessage>>
