package efedorov.model

import efedorov.serializer.DurationSerializer
import efedorov.serializer.InstantSerializer
import java.time.Duration
import java.time.Instant
import kotlinx.serialization.Serializable

@Serializable
data class HistoryEntry(
    val point: Point,
    val success: Boolean,
    @Serializable(with = InstantSerializer::class)
    val serverTime: Instant,
    @Serializable(with = DurationSerializer::class)
    val executionTime: Duration
)
