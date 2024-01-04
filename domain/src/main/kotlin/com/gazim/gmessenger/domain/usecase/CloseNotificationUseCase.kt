package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.repository.INotificationRepository

class CloseNotificationUseCase(private val notificationRepository: INotificationRepository) :
    ICloseNotificationUseCase {
    override suspend fun invoke() = notificationRepository.closeConnection()
}
