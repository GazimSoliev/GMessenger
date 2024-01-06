package com.gazim.gmessenger.app.model

class PrivateChatUI(
    override val identifier: String,
    override val title: String,
    override val user: IUserUI,
    override val chatName: String,
    override val chatLink: String,
) : IPrivateChatUI
