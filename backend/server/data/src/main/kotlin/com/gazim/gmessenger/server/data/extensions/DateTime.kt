package com.gazim.gmessenger.server.data.extensions

import java.time.LocalDateTime
import java.time.ZoneOffset

fun nowInUTC() = LocalDateTime.now(ZoneOffset.UTC)!!