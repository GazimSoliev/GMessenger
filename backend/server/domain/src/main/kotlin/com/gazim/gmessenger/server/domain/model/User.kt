package com.gazim.gmessenger.server.domain.model

import java.util.*

data class User(
    val id: UUID,
    val nickname: String,
    val username: String,
    val photo: Image?,
)
