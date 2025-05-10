package com.gazim.gmessenger.server.domain.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun nowInUTC() = Clock.System.now().toLocalDateTime(TimeZone.UTC)
