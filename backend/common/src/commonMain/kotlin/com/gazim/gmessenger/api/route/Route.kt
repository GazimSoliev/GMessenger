@file:Suppress("unused")

package com.gazim.gmessenger.api.route

import io.ktor.resources.*

@Resource("/chat")
class ChatRoute {
    @Resource("{id}")
    class Id(
        val id: String,
        val parent: ChatRoute = ChatRoute(),
    )
}

@Resource("/chats")
class ChatsRoute

@Resource("/create_chat")
class CreateChatRoute

@Resource("/find_user")
class FindUserRoute {
    @Resource("{query}")
    class Query(
        val query: String,
        val parent: FindUserRoute = FindUserRoute(),
    )
}

@Resource("/login")
class LoginRoute

@Resource("/notifications")
class NotificationsRoute

const val notificationRoute = "/notifications"

@Resource("/registration")
class RegistrationRoute

@Resource("/user")
class UserRoute

@Resource("/messages")
class MessagesRoute {
    @Resource("{chatId}")
    class ChatId(
        val chatId: String,
        val parent: MessagesRoute = MessagesRoute(),
    )
}

@Resource("/edit_profile")
class EditProfileRoute

@Resource("/upload_profile_photo")
class UploadProfilePhotoRoute {
    @Resource("{type}")
    class Type(
        val type: String,
        val parent: UploadProfilePhotoRoute = UploadProfilePhotoRoute(),
    )
}

@Resource("/image")
class ImageRoute {
    @Resource("{id}")
    class Id(
        val id: String,
        val parent: ImageRoute = ImageRoute(),
    )
}
