package lab9.backend.domain

import kotlinx.serialization.Serializable

@Serializable
sealed class Notification {
    @Serializable
    data class VehicleCreated(
            val vehicleId: Int,
            val authorId: Int
    ) : Notification()

    @Serializable
    data class AccessChanged(val fromUser: Int, val toUser: Int, val canEdit: Boolean, val canDelete: Boolean) :
            Notification()

    @Serializable
    data class VehicleDeleted(val vehicleId: Int, val deletedBy: Int) : Notification()

    @Serializable
    data class VehicleUpdated(val vehicleId: Int, val updatedBy: Int) : Notification()
}
