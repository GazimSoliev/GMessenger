package com.gazim.gmessenger.domain.model

data class ChatModel(
    override val identifier: String,
    override val title: String,
) : IChatModel
