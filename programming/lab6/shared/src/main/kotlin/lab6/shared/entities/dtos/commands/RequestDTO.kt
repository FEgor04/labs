package lab6.shared.entities.dtos.commands

import kotlinx.serialization.Serializable
import lab6.shared.entities.user.User
import lab6.shared.entities.vehicle.Vehicle
import lab6.shared.entities.vehicle.VehicleType
import java.util.UUID

/**
 * Класс-предок всех DTO запросов.
 */
@Serializable
sealed class RequestDTO {
    abstract val name: String
    abstract val user: User
}

@Serializable
data class AddRequestDTO(
    val vehicle: Vehicle,
    override val user: User,
) :
    RequestDTO() {
    override val name: String = "add"
}

@Serializable
data class ShowRequestDTO(
    override val user: User
) : RequestDTO() {
    override val name: String = "show"
}

@Serializable
data class ClearRequestDTO(
    override val user: User
) : RequestDTO() {
    override val name = "clear"
    override fun toString(): String {
        return "clear command"
    }
}

@Serializable
data class CountByTypeRequestDTO(
    val vehicleType: VehicleType?, override val user: User,
) : RequestDTO() {
    override val name = "count_by_type"
}

@Serializable
data class CountLessThanEnginePowerRequestDTO(
    val enginePower: Double, override val user: User
) : RequestDTO() {
    override val name = "count_less_than_engine_power"
}

@Serializable
data class InfoRequestDTO(override val user: User) : RequestDTO() {
    override val name = "info"
}

@Serializable
data class GetMinByIDRequestDTO(override val user: User) : RequestDTO() {
    override val name = "min_by_id"
}

@Serializable
data class RemoveGreaterRequestDTO(val vehicle: Vehicle, override val user: User) : RequestDTO() {
    override val name: String = "remove_greater"
}

@Serializable
data class RemoveRequestDTO(val id: Int, override val user: User) : RequestDTO() {
    override val name = "remove"
}

@Serializable
data class RemoveLowerRequestDTO(val veh: Vehicle, override val user: User) : RequestDTO() {
    override val name = "remove_lower"
}

@Serializable
data class ReplaceIfLowerRequestDTO(val id: Int, val veh: Vehicle, override val user: User) : RequestDTO() {
    override val name = "replace_if_lower"
}

@Serializable
data class UpdateVehicleByIdRequest(val id: Int, val veh: Vehicle, override val user: User) : RequestDTO() {
    override val name = "update"
}
