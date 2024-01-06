package com.gazim.gmessenger.domain.model

data class SentMessageModel(
    override val message: String,
) : ISentMessageModel
