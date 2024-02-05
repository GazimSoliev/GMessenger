package com.gazim.gmessenger.server.domain.model

class PrivateChat(
    override val identifier: String,
    override val title: String,
    override val user: IUser
) : IPrivateChat