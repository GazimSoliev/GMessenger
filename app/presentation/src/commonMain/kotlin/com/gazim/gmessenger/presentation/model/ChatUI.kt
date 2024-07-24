package com.gazim.gmessenger.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatUI(
    override val identifier: String,
    override val title: String,
    override val chatName: String,
    override val chatLink: String,
    override val image: String?,
) : IChatUI
