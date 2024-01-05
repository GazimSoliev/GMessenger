package com.gazim.gmessenger.data.repository

import com.gazim.gmessenger.client.pc.domain.model.IChatModel
import com.gazim.gmessenger.client.pc.domain.repository.IChatSessionRepository

// todo: Take out into UseCase
class ChatSessionRepository : IChatSessionRepository {
    override lateinit var currentChat: IChatModel
}
