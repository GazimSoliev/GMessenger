package com.gazim.gmessenger.data.repository

import com.gazim.gmessenger.domain.repository.ISessionRepository

// todo: Take out into UseCase
class SessionRepository : ISessionRepository {
    override var token: String = ""
}
