package com.gazim.gmessenger.presentation.model

import kotlinx.datetime.LocalDateTime

data class TheirMessageUI(
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: IUserUI,
) : ITheirMessageUI
