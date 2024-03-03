package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.Message
import com.gazim.gmessenger.server.domain.model.User
import kotlinx.coroutines.flow.Flow

class GetNotifications : IGetNotifications {
    override fun invoke(user: User): Flow<Message> {
        TODO("Not yet implemented")
    }
}
