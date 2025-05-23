package lab7.entities

import kotlinx.serialization.Serializable
import lab7.entities.vehicle.LocalDateSerializer
import java.time.LocalDate

@Serializable
data class CollectionInfo(
    val size: Int,
    @Serializable(with = LocalDateSerializer::class) val creationDate: LocalDate,
    val type: String
)
