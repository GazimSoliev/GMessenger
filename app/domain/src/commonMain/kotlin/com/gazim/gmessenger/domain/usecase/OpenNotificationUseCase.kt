package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.repository.INotificationRepository

class OpenNotificationUseCase(private val notificationRepository: INotificationRepository) : IOpenNotificationUseCase {
    override suspend fun invoke() = notificationRepository.openConnection()
}
