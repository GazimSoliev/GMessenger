package com.gazim.gmessenger.di

import com.gazim.gmessenger.app.features.chat.ChatViewModel
import com.gazim.gmessenger.app.features.chats.ChatsViewModel
import com.gazim.gmessenger.app.features.finduser.FindUserViewModel
import com.gazim.gmessenger.app.features.login.LoginViewModel
import com.gazim.gmessenger.app.features.register.RegisterViewModel
import com.gazim.gmessenger.app.features.user.UserViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val viewModelModule =
    module {
        factoryOf(::RegisterViewModel)
        factoryOf(::LoginViewModel)
        factoryOf(::ChatsViewModel)
        factoryOf(::FindUserViewModel)
        factoryOf(::ChatViewModel)
        factoryOf(::UserViewModel)
    }
