package lab9.backend.domain

sealed class Notification {
    data class NewVehicleNotification(val vehicleId: Vehicle.VehicleID) : Notification()
    data class VehicleDeletedNotification(val vehicleId: Vehicle.VehicleID, val deletedBy: User.UserID) : Notification()
    data class AccessGranted(
        val byWhom: User.UserID,
        val toWho: User.UserID,
        val canEdit: Boolean,
        val canDelete: Boolean
    )
}