package com.gazim.gmessenger.domain.model

class PrivateChat(
    override val id: String,
    override val title: String,
    val user: User,
) : IChat
