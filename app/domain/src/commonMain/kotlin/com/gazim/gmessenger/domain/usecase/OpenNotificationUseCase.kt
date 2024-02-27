package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.INotificationService

class OpenNotificationUseCase(private val notificationRepository: INotificationService) : IOpenNotificationUseCase {
    override suspend fun invoke() = notificationRepository.openConnection()
}
