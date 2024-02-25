@file:Suppress("HttpUrlsUsage")

package com.gazim.gmessenger.api

import com.gmessenger.backend.config.BuildKonfig

var ipServer = BuildKonfig.host
var urlServer = "${BuildKonfig.hostPrefix}$ipServer"
var wsPrefix = "ws://"
var wsServer = "$wsPrefix$ipServer"