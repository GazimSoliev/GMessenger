package com.gazim.gmessenger.domain.model

data class Chat(
    override val id: String,
    override val title: String,
) : IChatModel
