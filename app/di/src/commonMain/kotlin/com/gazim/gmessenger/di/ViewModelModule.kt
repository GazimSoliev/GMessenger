package com.gazim.gmessenger.di

import com.gazim.gmessenger.presentation.features.chat.ChatViewModel
import com.gazim.gmessenger.presentation.features.chats.ChatsViewModel
import com.gazim.gmessenger.presentation.features.finduser.FindUserViewModel
import com.gazim.gmessenger.presentation.features.login.LoginViewModel
import com.gazim.gmessenger.presentation.features.register.RegisterViewModel
import com.gazim.gmessenger.presentation.features.selectserver.SelectServerViewModel
import com.gazim.gmessenger.presentation.features.user.UserViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val viewModelModule: Module =
    module {
        factoryOf(::RegisterViewModel)
        factoryOf(::LoginViewModel)
        factoryOf(::ChatsViewModel)
        factoryOf(::FindUserViewModel)
        factoryOf(::ChatViewModel)
        factoryOf(::UserViewModel)
        factoryOf(::SelectServerViewModel)
    }
