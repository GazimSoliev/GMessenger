@file:Suppress("unused")

package com.gazim.gmessenger.core.route

import io.ktor.resources.*

@Resource("/chat")
public class ChatRoute {
    @Resource("{id}")
    public class Id(
        public val id: String,
        public val parent: ChatRoute = ChatRoute(),
    )
}

@Resource("/get_chat")
public class GetChatRoute {
    @Resource("{id}")
    public class Id(
        public val id: String,
        public val parent: GetChatRoute = GetChatRoute(),
    )
}

@Resource("/chats")
public class ChatsRoute

@Resource("/create_chat")
public class CreateChatRoute

@Resource("/find_user")
public class FindUserRoute {
    @Resource("{query}")
    public class Query(
        public val query: String,
        public val parent: FindUserRoute = FindUserRoute(),
    )
}

@Resource("/login")
public class LoginRoute

@Resource("/notifications")
public class NotificationsRoute

public const val notificationRoute: String = "/notifications"

@Resource("/registration")
public class RegistrationRoute

@Resource("/user")
public class UserRoute

@Resource("/messages")
public class MessagesRoute {
    @Resource("{chatId}")
    public class ChatId(
        public val chatId: String,
        public val parent: MessagesRoute = MessagesRoute(),
    )
}

@Resource("/edit_profile")
public class EditProfileRoute

@Resource("/upload_profile_photo")
public class UploadProfilePhotoRoute {
    @Resource("{type}")
    public class Type(
        public val type: String,
        public val parent: UploadProfilePhotoRoute = UploadProfilePhotoRoute(),
    )
}

@Resource("/image")
public class ImageRoute {
    @Resource("{id}")
    public class Id(
        public val id: String,
        public val parent: ImageRoute = ImageRoute(),
    )
}

@Resource("/ping")
public class PingRoute
