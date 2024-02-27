package com.gazim.gmessenger.data.service

import com.gazim.gmessenger.domain.service.ISessionService

// todo: Take out into UseCase
class SessionService : ISessionService {
    override var token: String = ""
}
