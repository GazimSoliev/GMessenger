package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.INotificationModel
import com.gazim.gmessenger.domain.repository.INotificationRepository
import kotlinx.coroutines.flow.Flow

class GetNotificationsUseCase(private val notificationRepository: INotificationRepository) : IGetNotificationsUseCase {
    override suspend fun invoke(): Flow<INotificationModel> = notificationRepository.notifications
}
