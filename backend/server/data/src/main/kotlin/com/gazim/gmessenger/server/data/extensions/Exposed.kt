package com.gazim.gmessenger.server.data.extensions

import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalUuidApi::class)
operator fun <ID: UUIDEntity, T : UUIDEntityClass<ID>> T.get(id: Uuid) = get(id.toJavaUuid())
