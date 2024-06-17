package com.gazim.gmessenger.domain.service

import com.gazim.gmessenger.domain.model.INotificationWebSocketModel
import com.gazim.gmessenger.domain.model.Notification
import kotlinx.coroutines.flow.Flow

// todo: Take out into UseCase
class NotificationServiceImpl(
    private val notificationWebSocket: INotificationWebSocketModel,
) : NotificationService {
    override val notifications: Flow<Notification> =
        notificationWebSocket.notifications

    override suspend fun openConnection() = notificationWebSocket.openConnection()

    override suspend fun closeConnection() = notificationWebSocket.closeConnection()
}
