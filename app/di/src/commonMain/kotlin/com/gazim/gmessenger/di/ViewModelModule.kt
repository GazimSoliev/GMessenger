package com.gazim.gmessenger.di

import com.gazim.gmessenger.presentation.features.chat.ChatViewModel
import com.gazim.gmessenger.presentation.features.chats.ChatsViewModel
import com.gazim.gmessenger.presentation.features.finduser.FindUserViewModel
import com.gazim.gmessenger.presentation.features.login.LoginViewModel
import com.gazim.gmessenger.presentation.features.register.RegisterViewModel
import com.gazim.gmessenger.presentation.features.selectserver.SelectServerViewModel
import com.gazim.gmessenger.presentation.features.user.UserViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module =
    module {
        viewModelOf(::RegisterViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::ChatsViewModel)
        viewModelOf(::FindUserViewModel)
        viewModelOf(::ChatViewModel)
        viewModelOf(::UserViewModel)
        viewModelOf(::SelectServerViewModel)
    }
