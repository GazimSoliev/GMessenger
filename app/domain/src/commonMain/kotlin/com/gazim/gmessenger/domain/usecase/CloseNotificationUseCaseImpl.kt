package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.NotificationService

class CloseNotificationUseCaseImpl(
    private val notificationRepository: NotificationService,
) : CloseNotificationUseCase {
    override suspend fun invoke() = runCatching { notificationRepository.closeConnection() }
}
