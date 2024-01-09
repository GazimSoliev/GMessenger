package com.gazim.gmessenger.server.module

import com.gazim.gmessenger.server.model.INotification
import com.gazim.gmessenger.server.model.IUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Suppress("MemberVisibilityCanBePrivate")
class NotificationModule : INotificationModule {
    val hashMap = HashMap<IUser, MutableSharedFlow<INotification>>()

    override suspend fun getNotifications(user: IUser): Flow<INotification> = getOrNew(user).asSharedFlow()

    override suspend fun sendNotification(
        user: IUser,
        notification: INotification,
    ) = getOrNew(user).emit(notification)

    fun getOrNew(user: IUser): MutableSharedFlow<INotification> {
        var flow = hashMap[user]
        if (flow == null) {
            synchronized(this) {
                flow = hashMap[user] ?: MutableSharedFlow<INotification>().also { hashMap[user] = it }
            }
        }
        return flow!!
    }
}
