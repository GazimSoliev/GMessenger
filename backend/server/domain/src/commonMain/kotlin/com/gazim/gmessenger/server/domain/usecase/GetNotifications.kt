package com.gazim.gmessenger.server.domain.usecase

import com.gazim.gmessenger.server.domain.model.IMessage
import com.gazim.gmessenger.server.domain.model.IUser
import kotlinx.coroutines.flow.Flow

class GetNotifications : IGetNotifications {
    override fun invoke(user: IUser): Flow<IMessage> {
        TODO("Not yet implemented")
    }
}
