package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.model.Notification
import com.gazim.gmessenger.domain.service.NotificationService
import kotlinx.coroutines.flow.Flow

class GetNotificationsUseCaseImpl(
    private val notificationRepository: NotificationService,
) : GetNotificationsUseCase {
    override suspend fun invoke(): Flow<Notification> = notificationRepository.notifications
}
