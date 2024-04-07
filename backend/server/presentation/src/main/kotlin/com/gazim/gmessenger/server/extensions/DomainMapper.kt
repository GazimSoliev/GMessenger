package com.gazim.gmessenger.server.extensions

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.util.*
import com.gazim.gmessenger.api.model.AuthenticationForm as AuthenticationFormAPI
import com.gazim.gmessenger.api.model.Chat as ChatAPI
import com.gazim.gmessenger.api.model.Message as MessageAPI
import com.gazim.gmessenger.api.model.MessageForm as MessageFormAPI
import com.gazim.gmessenger.api.model.MessagePage as MessagePageAPI
import com.gazim.gmessenger.api.model.MessagePageKey as MessagePageKeyAPI
import com.gazim.gmessenger.api.model.PrivateChat as PrivateChatAPI
import com.gazim.gmessenger.api.model.ProfileForm as ProfileFormAPI
import com.gazim.gmessenger.api.model.RegistrationForm as RegistrationFormAPI
import com.gazim.gmessenger.api.model.User as UserAPI

fun AuthenticationFormAPI.toDomain(): AuthenticationForm =
    AuthenticationForm(
        login = login,
        password = password,
    )

fun RegistrationFormAPI.toDomain() =
    RegistrationForm(
        nickname = nickname,
        username = username,
        login = login,
        password = password,
    )

fun User.toAPI() =
    UserAPI(
        id = id.toString(),
        nickname = nickname,
        username = username,
    )

fun UserAPI.toDomain() =
    User(
        id = UUID.fromString(id),
        nickname = nickname,
        username = username,
    )

fun IChat.toAPI() =
    if (this is PrivateChat) {
        PrivateChatAPI(
            id = id.toString(),
            title = title,
            user = user.toAPI(),
        )
    } else {
        ChatAPI(
            id = id.toString(),
            title = title,
        )
    }

fun MessageFormAPI.toDomain() = MessageForm(message = message)

fun Message.toAPI() =
    MessageAPI(
        id = id.toString(),
        message = message,
        sentAt = sentAt.toKotlinLocalDateTime(),
        user = user.toAPI(),
    )

fun MessagePageKeyAPI.toDomain() =
    MessagePageKey(
        start = start.toJavaLocalDateTime(),
        end = end.toJavaLocalDateTime(),
    )

fun MessagePageKey.toAPI() =
    MessagePageKeyAPI(
        start = start.toKotlinLocalDateTime(),
        end = end.toKotlinLocalDateTime(),
    )

fun MessagePage.toAPI() =
    MessagePageAPI(
        data = data.map(Message::toAPI),
        next = next?.toAPI(),
        prev = prev?.toAPI(),
    )

fun ProfileFormAPI.toDomain() =
    ProfileForm(
        nickname = nickname,
        username = username,
    )
