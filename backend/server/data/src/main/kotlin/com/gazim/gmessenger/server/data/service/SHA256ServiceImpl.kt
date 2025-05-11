package com.gazim.gmessenger.server.data.service

import com.gazim.gmessenger.server.domain.service.SHA256Service
import java.security.MessageDigest

class SHA256ServiceImpl : SHA256Service {
    private val messageDigest = MessageDigest.getInstance("SHA-256")

    override fun encode(bytes: ByteArray): ByteArray = messageDigest.digest(bytes)
}