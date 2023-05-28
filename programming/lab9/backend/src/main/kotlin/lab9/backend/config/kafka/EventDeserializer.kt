package lab9.backend.config.kafka

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import lab9.backend.domain.Event
import lab9.backend.logger.KCoolLogger
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import kotlin.text.Charsets.UTF_8

class EventDeserializer : Deserializer<Event> {
    private val serializer = Json
    private val logger by KCoolLogger()

    @Throws(SerializationException::class)
    override fun deserialize(topic: String?, data: ByteArray?): Event? {
        return try {
            serializer.decodeFromString(
                String(data ?: throw SerializationException("empty data"), UTF_8)
            )
        } catch(e: Exception) {
            logger.error("could not deserialize message from kafka, {}", e)
            null
        }
    }

}
