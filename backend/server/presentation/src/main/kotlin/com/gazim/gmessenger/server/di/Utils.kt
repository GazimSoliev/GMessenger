package com.gazim.gmessenger.server.di

import com.gazim.gmessenger.server.utils.ISecurityUtils
import com.gazim.gmessenger.server.utils.SecurityUtils
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val utils =
    module {
        singleOf(::SecurityUtils) bind ISecurityUtils::class
    }
