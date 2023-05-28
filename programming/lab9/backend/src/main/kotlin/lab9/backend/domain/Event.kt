package lab9.backend.domain

import kotlinx.serialization.Serializable

@Serializable
sealed class Event {
    @Serializable
    data class NewVehicle(
        val vehicleId: Vehicle.VehicleID,
        val authorID: User.UserID,
    ) :
        Event()

    @Serializable
    data class VehicleDeleted(
        val vehicleId: Vehicle.VehicleID, val deletedBy: User.UserID,
    ) : Event()

    @Serializable
    data class AccessChanged(
        val byWhom: User.UserID,
        val toWho: User.UserID,
        val canEdit: Boolean,
        val canDelete: Boolean
    ) : Event()

    @Serializable
    data class VehicleUpdated(
        val vehicleID: Vehicle.VehicleID,
        val actorId: User.UserID,
    ): Event()
}