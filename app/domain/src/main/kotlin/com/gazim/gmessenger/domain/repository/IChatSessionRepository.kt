package com.gazim.gmessenger.domain.repository

import com.gazim.gmessenger.domain.model.IChatModel

interface IChatSessionRepository {
    var currentChat: IChatModel
}
