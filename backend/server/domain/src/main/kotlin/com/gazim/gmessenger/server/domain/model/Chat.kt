package com.gazim.gmessenger.server.domain.model

import java.util.*

data class Chat(
    override val id: UUID,
    override val title: String,
) : IChat
