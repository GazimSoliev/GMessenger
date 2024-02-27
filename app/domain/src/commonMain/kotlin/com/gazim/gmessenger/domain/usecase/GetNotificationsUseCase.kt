package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.INotificationModel
import com.gazim.gmessenger.domain.service.INotificationService
import kotlinx.coroutines.flow.Flow

class GetNotificationsUseCase(private val notificationRepository: INotificationService) : IGetNotificationsUseCase {
    override suspend fun invoke(): Flow<INotificationModel> = notificationRepository.notifications
}
