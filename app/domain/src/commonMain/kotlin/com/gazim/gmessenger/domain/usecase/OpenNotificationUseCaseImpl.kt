package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.NotificationService

class OpenNotificationUseCaseImpl(
    private val notificationRepository: NotificationService,
) : OpenNotificationUseCase {
    override suspend fun invoke() = notificationRepository.openConnection()
}
