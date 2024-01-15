package com.gazim.gmessenger.server.domain.model

interface IUser {
    val nickname: String
    val username: String
}

interface IChat {

}

interface IMessage

interface ILoginPassword {
    val login: String
    val password: String
}

interface IAccount : IUser, ILoginPassword