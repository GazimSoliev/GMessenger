package com.gazim.gmessenger.data.service

import com.gazim.gmessenger.domain.service.ISessionService
import java.util.*

// todo: Take out into UseCase
class SessionService : ISessionService {
    private var currentToken: String? = null
    private var currentSession: String? = null

    override fun currentToken(): String? = currentToken

    override fun currentSession(): String? = currentSession

    override fun setSession(token: String) {
        currentToken = token
        currentSession = UUID.randomUUID().toString()
    }

    override fun clearSession() {
        currentToken = null
        currentSession = null
    }
}
