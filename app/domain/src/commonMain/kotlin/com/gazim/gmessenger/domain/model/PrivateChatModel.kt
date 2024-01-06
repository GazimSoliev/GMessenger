package com.gazim.gmessenger.domain.model

class PrivateChatModel(
    override val identifier: String,
    override val title: String,
    override val user: IUserModel,
) : IPrivateChatModel
