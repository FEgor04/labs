package lab9.backend.config.kafka

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab9.backend.domain.Event
import org.apache.kafka.common.serialization.Serializer
import java.nio.charset.Charset

class EventSerializer : Serializer<Event> {
    private val serializer = Json

    override fun serialize(topic: String?, data: Event?): ByteArray {
        return serializer.encodeToString(data).toByteArray(Charset.forName("UTF-8"))
    }
}