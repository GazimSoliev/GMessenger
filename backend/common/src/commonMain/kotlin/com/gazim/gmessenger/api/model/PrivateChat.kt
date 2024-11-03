package com.gazim.gmessenger.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.com.gazim.gmessenger.serialization.UuidSerializer
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
@SerialName("PrivateChat")
data class PrivateChat(
    @Serializable(UuidSerializer::class)
    override val id: Uuid,
    override val title: String,
    val user: User,
) : IChat
