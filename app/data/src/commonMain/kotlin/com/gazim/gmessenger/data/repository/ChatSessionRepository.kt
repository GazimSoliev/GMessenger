package com.gazim.gmessenger.data.repository

import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.repository.IChatSessionRepository

// todo: Take out into UseCase
class ChatSessionRepository : IChatSessionRepository {
    override lateinit var currentChat: IChatModel
}
