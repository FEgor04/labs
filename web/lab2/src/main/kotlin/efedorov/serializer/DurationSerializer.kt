package efedorov.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Duration

object DurationSerializer : KSerializer<Duration> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.time.Duration", PrimitiveKind.LONG)
    override fun deserialize(decoder: Decoder): Duration {
        return Duration.ofNanos(decoder.decodeLong())
    }

    override fun serialize(encoder: Encoder, value: Duration) {
        return encoder.encodeLong(value.toNanos())
    }
}