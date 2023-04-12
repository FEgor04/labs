package lab8.entities.events

import kotlinx.serialization.Serializable
import lab8.entities.vehicle.Vehicle

@Serializable
sealed class CollectionEvent(val name: String) {
    @Serializable
    class ForceUpdate(): CollectionEvent("update")
}