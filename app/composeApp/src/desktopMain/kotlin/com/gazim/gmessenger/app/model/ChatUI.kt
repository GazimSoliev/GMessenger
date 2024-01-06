package com.gazim.gmessenger.app.model

data class ChatUI(
    override val identifier: String,
    override val title: String,
    override val chatName: String,
    override val chatLink: String,
) : IChatUI
