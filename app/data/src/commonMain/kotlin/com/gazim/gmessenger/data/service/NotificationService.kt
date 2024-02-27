package com.gazim.gmessenger.data.service

import com.gazim.gmessenger.domain.model.INotificationModel
import com.gazim.gmessenger.domain.model.INotificationWebSocketModel
import com.gazim.gmessenger.domain.service.INotificationService
import kotlinx.coroutines.flow.Flow

// todo: Take out into UseCase
class NotificationService(private val notificationWebSocket: INotificationWebSocketModel) : INotificationService {
    override val notifications: Flow<INotificationModel> =
        notificationWebSocket.notifications

    override suspend fun openConnection() = notificationWebSocket.openConnection()

    override suspend fun closeConnection() = notificationWebSocket.closeConnection()
}
