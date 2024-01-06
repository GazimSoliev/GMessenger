package com.gazim.gmessenger.app.model

data class LoginPasswordUI(
    override val login: String,
    override val password: String,
) : ILoginPasswordUI
