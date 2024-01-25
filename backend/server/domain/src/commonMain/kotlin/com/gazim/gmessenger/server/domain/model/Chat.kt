package com.gazim.gmessenger.server.domain.model

data class Chat(
    override val identifier: String,
    override val title: String,
) : IChat
