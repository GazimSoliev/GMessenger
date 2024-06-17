package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.NotificationService

class GetNotificationsUseCaseImpl(
    private val notificationRepository: NotificationService,
) : GetNotificationsUseCase {
    override suspend fun invoke() =
        runCatching {
            notificationRepository.notifications
        }
}
