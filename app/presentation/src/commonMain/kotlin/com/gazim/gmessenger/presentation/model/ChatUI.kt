package com.gazim.gmessenger.presentation.model

import kotlinx.serialization.Serializable
import org.example.com.gazim.gmessenger.serialization.UuidSerializer
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class ChatUI(
    @Serializable(UuidSerializer::class)
    override val identifier: Uuid,
    override val title: String,
    override val chatName: String,
    override val chatLink: String,
    @Serializable(UuidSerializer::class)
    override val image: Uuid?,
) : IChatUI
