package com.gazim.gmessenger.server.data.extensions

import org.jetbrains.exposed.dao.UUIDEntityClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalUuidApi::class)
operator fun <ID, T : UUIDEntityClass<ID>> T.get(id: Uuid) = get(id.toJavaUuid())
