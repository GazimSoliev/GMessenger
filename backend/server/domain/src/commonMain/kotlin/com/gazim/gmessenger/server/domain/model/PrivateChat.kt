package com.gazim.gmessenger.server.domain.model

import java.util.*

class PrivateChat(
    override val id: UUID,
    override val title: String,
    val user: User,
) : IChat
