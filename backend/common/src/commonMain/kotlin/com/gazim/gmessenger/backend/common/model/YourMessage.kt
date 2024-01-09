package com.gazim.gmessenger.backend.common.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("YourMessage")
data class YourMessage(
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: IUser,
) : IYourMessage
