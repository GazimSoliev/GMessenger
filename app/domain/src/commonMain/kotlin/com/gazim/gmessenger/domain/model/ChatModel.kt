package com.gazim.gmessenger.domain.model

data class ChatModel(
    override val id: String,
    override val title: String,
) : IChatModel
