package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.User
import kotlinx.coroutines.flow.Flow

public class GetNotificationsImpl : GetNotifications {
    override fun invoke(user: User): Flow<Message> {
        TODO("Not yet implemented")
    }
}
