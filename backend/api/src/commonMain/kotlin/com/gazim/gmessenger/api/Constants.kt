@file:Suppress("HttpUrlsUsage")

package com.gazim.gmessenger.api

import com.gmessenger.backend.config.BuildKonfig

var ipServer = BuildKonfig.host
var hostPrefix = BuildKonfig.hostPrefix
var wsPrefix = BuildKonfig.wsPrefix
var urlServer = "$hostPrefix$ipServer"
var wsServer = "$wsPrefix$ipServer"
