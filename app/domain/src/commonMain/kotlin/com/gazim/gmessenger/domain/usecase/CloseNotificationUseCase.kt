package com.gazim.gmessenger.domain.usecase

import com.gazim.gmessenger.domain.service.INotificationService

class CloseNotificationUseCase(private val notificationRepository: INotificationService) :
    ICloseNotificationUseCase {
    override suspend fun invoke() = notificationRepository.closeConnection()
}
