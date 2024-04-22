package com.gazim.gmessenger.presentation.service

import com.gazim.gmessenger.domain.model.INotificationModel
import com.gazim.gmessenger.domain.usecase.CloseNotificationUseCase
import com.gazim.gmessenger.domain.usecase.GetNotificationsUseCase
import com.gazim.gmessenger.domain.usecase.OpenNotificationUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll

// todo: Review?
class NotificationService(
    private val openNotificationUseCase: OpenNotificationUseCase,
    private val closeNotificationUseCase: CloseNotificationUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase,
) : INotificationService {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private lateinit var notifications: MutableSharedFlow<INotificationModel>
    private var job: Job = Job()

    override fun subscribe(notifications: MutableSharedFlow<INotificationModel>) {
        this.notifications = notifications
    }

    override fun start() {
        job =
            scope.launch {
                launch {
                    notifications.emitAll(getNotificationsUseCase())
                }
                openNotificationUseCase()
            }
    }

    override fun stop() {
        scope.launch {
            closeNotificationUseCase()
            job.cancel()
        }
    }
}
