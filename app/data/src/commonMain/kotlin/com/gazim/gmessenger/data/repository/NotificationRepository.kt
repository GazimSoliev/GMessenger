package com.gazim.gmessenger.data.repository

import com.gazim.gmessenger.client.pc.domain.model.INotificationModel
import com.gazim.gmessenger.client.pc.domain.model.INotificationWebSocketModel
import com.gazim.gmessenger.client.pc.domain.repository.INotificationRepository
import kotlinx.coroutines.flow.Flow

// todo: Take out into UseCase
class NotificationRepository(private val notificationWebSocket: INotificationWebSocketModel) : INotificationRepository {
    override val notifications: Flow<INotificationModel> =
        notificationWebSocket.notifications

    override suspend fun openConnection() = notificationWebSocket.openConnection()

    override suspend fun closeConnection() = notificationWebSocket.closeConnection()
}
