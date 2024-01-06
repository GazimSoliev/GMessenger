package com.gazim.gmessenger.app.model

import kotlinx.datetime.LocalDateTime

data class YourMessageUI(
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: IUserUI,
) : IYourMessageUI
