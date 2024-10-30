@file:OptIn(ExperimentalUuidApi::class)

package com.gazim.gmessenger.server.extensions

import com.gazim.gmessenger.server.domain.model.*
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import com.gazim.gmessenger.api.model.AuthenticationForm as AuthenticationFormAPI
import com.gazim.gmessenger.api.model.Chat as ChatAPI
import com.gazim.gmessenger.api.model.Image as ImageAPI
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
        id = id,
        nickname = nickname,
        username = username,
        photo = photo?.toAPI(),
    )

fun UserAPI.toDomain() =
    User(
        id = id,
        nickname = nickname,
        username = username,
        // TODO: Solve it
        photo = null,
    )

fun IChat.toAPI() =
    if (this is PrivateChat) {
        PrivateChatAPI(
            id = id,
            title = title,
            user = user.toAPI(),
        )
    } else {
        ChatAPI(
            id = id,
            title = title,
        )
    }

fun MessageFormAPI.toDomain() = MessageForm(message = message)

fun Message.toAPI() =
    MessageAPI(
        id = id,
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

fun Image.toAPI() =
    ImageAPI(
        id = id,
        type = type,
    )
