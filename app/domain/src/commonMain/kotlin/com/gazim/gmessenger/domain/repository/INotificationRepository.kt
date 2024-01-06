package com.gazim.gmessenger.domain.repository

import com.gazim.gmessenger.domain.model.INotificationModel
import kotlinx.coroutines.flow.Flow

interface INotificationRepository {
    val notifications: Flow<INotificationModel>

    suspend fun openConnection()

    suspend fun closeConnection()
}
