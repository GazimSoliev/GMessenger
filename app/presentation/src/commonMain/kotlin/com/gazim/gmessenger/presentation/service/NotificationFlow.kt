package com.gazim.gmessenger.presentation.service

import com.gazim.gmessenger.domain.model.Notification
import kotlinx.coroutines.flow.MutableSharedFlow

val notificationsReceiver = MutableSharedFlow<Notification>()
