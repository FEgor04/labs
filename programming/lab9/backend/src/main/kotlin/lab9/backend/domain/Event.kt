package lab9.backend.domain

sealed class Event {
    data class NewVehicle(
        val vehicleId: Vehicle.VehicleID,
    ) :
        Event()

    data class VehicleDeleted(
        val vehicleId: Vehicle.VehicleID, val deletedBy: User.UserID,
    ) : Event()

    data class AccessGranted(
        val byWhom: User.UserID,
        val toWho: User.UserID,
        val canEdit: Boolean,
        val canDelete: Boolean
    ) : Event()
}