package com.gazim.gmessenger.server.domain.model

import java.util.*

data class PrivateChat(
    override val id: UUID,
    override val title: String,
    val user: User,
) : IChat
