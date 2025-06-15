package com.gazim.gmessenger.server.extensions

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.server.websocket.webSocket
import io.ktor.util.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

val ResourceInstanceKey: AttributeKey<Any> = AttributeKey("ResourceInstance")

inline fun <reified T : Any> Route.webSocket(
    protocol: String? = null,
    noinline body: suspend DefaultWebSocketServerSession.(T) -> Unit,
) {
    resource<T> {
        handle(protocol, body)
    }
}

/**
 * Registers a handler [body] for a resource defined by the [T] class.
 *
 * @param body receives an instance of the typed resource [T] as the first parameter.
 */
inline fun <reified T : Any> Route.handle(
    protocol: String? = null,
    noinline body: suspend DefaultWebSocketServerSession.(T) -> Unit,
) {
    val serializer = serializer<T>()
    handle(serializer, protocol, body)
}

/**
 * Registers a handler [body] for a resource defined by the [T] class.
 *
 * @param serializer is used to decode the parameters of the request to an instance of the typed resource [T].
 * @param body receives an instance of the typed resource [T] as the first parameter.
 */
@Suppress("TooGenericExceptionCaught")
fun <T : Any> Route.handle(
    serializer: KSerializer<T>,
    protocol: String? = null,
    body: suspend DefaultWebSocketServerSession.(T) -> Unit,
) {
    val plugin =
        createRouteScopedPlugin("ResourceInstancePlugin") {
            onCall { call ->
                val resources = call.application.plugin(Resources)
                try {
                    val resource = resources.resourcesFormat.decodeFromParameters(serializer, call.parameters) as Any
                    call.attributes.put(ResourceInstanceKey, resource)
                } catch (cause: Throwable) {
                    throw BadRequestException("Can't transform call to resource", cause)
                }
            }
        }
    install(plugin)

    webSocket(protocol = protocol) {
        @Suppress("UNCHECKED_CAST")
        val resource = call.attributes[ResourceInstanceKey] as T
        body(resource)
    }
}
