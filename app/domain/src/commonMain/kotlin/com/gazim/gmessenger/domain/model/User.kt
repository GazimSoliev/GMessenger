package com.gazim.gmessenger.domain.model

data class User(
    val id: String,
    val nickname: String,
    val username: String,
    val photo: Image?,
)
