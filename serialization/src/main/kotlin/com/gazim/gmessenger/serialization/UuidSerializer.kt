package org.example.com.gazim.gmessenger.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class UuidSerializer : KSerializer<Uuid> {
    override val descriptor = PrimitiveSerialDescriptor(Uuid::class.qualifiedName!!, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Uuid {
        val str = decoder.decodeString()
        return Uuid.parse(str)
    }

    override fun serialize(encoder: Encoder, value: Uuid) {
        val str = value.toString()
        encoder.encodeString(str)
    }
}