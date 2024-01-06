package com.gazim.gmessenger.presentation.model

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

sealed interface IUserUI {
    val nickname: String
    val username: String
}

sealed interface ILoginUI {
    val login: String
}

sealed interface IPasswordUI {
    val password: String
}

sealed interface ILoginPasswordUI : ILoginUI, IPasswordUI

sealed interface IAccountUI : IUserUI, ILoginPasswordUI

sealed interface IMessageItemUI

sealed interface IGroupedMessagesDateUI : IMessageItemUI {
    val date: LocalDate
}

sealed interface IMessageUI {
    val message: String
}

sealed interface ISentMessageUI : IMessageUI

sealed interface IFullMessageUI : IMessageUI, IMessageItemUI {
    val sentAt: LocalDateTime
    val user: IUserUI
}

sealed interface ITheirMessageUI : IFullMessageUI

sealed interface IYourMessageUI : IFullMessageUI

sealed interface IChatUI {
    val identifier: String
    val title: String
    val chatName: String
    val chatLink: String
}

sealed interface IPrivateChatUI : IChatUI {
    val user: IUserUI
}

// todo: Review this
interface IChatWebSocketUI {
    val messages: Flow<List<IFullMessageUI>>

    suspend fun sendMessage(msg: IMessageUI)

    suspend fun openConnection()

    fun close()
}
