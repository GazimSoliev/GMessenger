package com.gazim.gmessenger.domain.service

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class SessionServiceImpl : SessionService {
    private var currentToken: String? = null
    private var currentSession: String? = null

    override fun currentToken(): String? = currentToken

    override fun currentSession(): String? = currentSession

    override fun setSession(token: String) {
        currentToken = token
        currentSession = Uuid.random().toString()
    }

    override fun clearSession() {
        currentToken = null
        currentSession = null
    }
}
