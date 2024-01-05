package com.gazim.gmessenger.data.model

data class ChatData(
    override val identifier: String,
    override val title: String,
) : IChatData
