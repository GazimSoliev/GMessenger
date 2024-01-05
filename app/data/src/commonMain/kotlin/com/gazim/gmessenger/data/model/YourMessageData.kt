package com.gazim.gmessenger.data.model

import kotlinx.datetime.LocalDateTime

data class YourMessageData(
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: IUserData,
) : IYourMessageData
