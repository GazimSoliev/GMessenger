package com.gazim.gmessenger.presentation.service

import com.gazim.gmessenger.domain.model.INotificationModel
import kotlinx.coroutines.flow.MutableSharedFlow

// todo: Review?
interface IService {
    fun start()

    fun stop()
}

interface INotificationService : IService {
    fun subscribe(notifications: MutableSharedFlow<INotificationModel>)
}

