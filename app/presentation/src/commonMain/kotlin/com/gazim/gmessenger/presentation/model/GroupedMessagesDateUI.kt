package com.gazim.gmessenger.presentation.model

import kotlinx.datetime.LocalDate

@JvmInline
value class GroupedMessagesDateUI(
    val date: LocalDate,
) : IMessageItemUI
