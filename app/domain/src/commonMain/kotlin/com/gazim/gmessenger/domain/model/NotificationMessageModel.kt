package com.gazim.gmessenger.domain.model

import kotlinx.datetime.LocalDateTime

data class NotificationMessageModel(
    override val id: String,
    override val message: String,
    override val sentAt: LocalDateTime,
    override val user: IUserModel,
    override val chatName: String,
) : INotificationMessageModel
