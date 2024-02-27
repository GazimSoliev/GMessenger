package com.gazim.gmessenger.data.service

import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.service.IChatSessionService

// todo: Take out into UseCase
class ChatSessionService : IChatSessionService {
    override lateinit var currentChat: IChatModel
}
