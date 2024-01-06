package com.gazim.gmessenger.presentation.model

data class LoginPasswordUI(
    override val login: String,
    override val password: String,
) : ILoginPasswordUI
