package com.gazim.gmessenger.app.service

import com.gazim.gmessenger.domain.model.INotificationModel
import kotlinx.coroutines.flow.MutableSharedFlow

val notificationsReceiver = MutableSharedFlow<INotificationModel>()