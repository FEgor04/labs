package lab9.backend.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.*

class LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("localDate", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.ofInstant(Instant.ofEpochSecond(decoder.decodeLong()), ZoneId.systemDefault())
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        return encoder.encodeLong(value.toEpochSecond(LocalTime.NOON, ZoneOffset.UTC))
    }
}