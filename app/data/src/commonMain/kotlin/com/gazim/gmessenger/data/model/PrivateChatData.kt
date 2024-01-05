package com.gazim.gmessenger.data.model

class PrivateChatData(
    override val identifier: String,
    override val title: String,
    override val user: IUserData,
) : IPrivateChatData
