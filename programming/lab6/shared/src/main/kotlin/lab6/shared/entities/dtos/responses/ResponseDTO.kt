package lab6.shared.entities.dtos.responses

import kotlinx.serialization.Serializable
import lab6.shared.entities.CollectionInfo
import lab6.shared.entities.vehicle.Vehicle

/**
 * Класс-предок всех DTO ответов.
 */
@Serializable
sealed class ResponseDTO() {
    abstract val name: String
    abstract val error: String?
}

@Serializable
data class NoSuchCommandResponseDTO(override val name: String) : ResponseDTO() {
    override val error = "No such command"
}

@Serializable
data class AddResponseDTO(
    override val error: String?,
    val newId: Int?
) : ResponseDTO() {
    override val name = "add"
}

@Serializable
data class ShowResponseDTO(
    override val error: String?,
    val vehicles: List<Vehicle>
) : ResponseDTO() {
    override val name = "show"
}

@Serializable
data class ClearResponseDTO(
    override val error: String?
) : ResponseDTO() {
    override val name = "clear"
}

@Serializable
data class CountByTypeResponseDTO(
    val count: Int, override val error: String? = null
) : ResponseDTO() {
    override val name = "count_by_type"
}

@Serializable
data class CountLessThanEnginePowerResponseDTO(
    val count: Int, override val error: String? = null
) : ResponseDTO() {
    override val name = "count_by_less_than_engine_power"
}

@Serializable
data class InfoResponseDTO(
    val info: CollectionInfo, override val error: String? = null
) : ResponseDTO() {
    override val name = "count_by_less_than_engine_power"
}

@Serializable
data class GetMinByIDResponseDTO(
    val min: Vehicle?, override val error: String? = null
) : ResponseDTO() {
    override val name = "count_by_less_than_engine_power"
}

@Serializable
data class RemoveGreaterResponseDTO(val cnt: Int, override val error: String?) : ResponseDTO() {
    override val name: String = "remove_greater"
}

@Serializable
data class RemoveResponseDTO(override val error: String?) : ResponseDTO() {
    override val name = "remove"
}

@Serializable
data class RemoveLowerResponseDTO(val cnt: Int, override val error: String?) : ResponseDTO() {
    override val name = "remove_greater"
}

@Serializable
data class ReplaceIfLowerResponseDTO(val result: ReplaceIfLowerResults, override val error: String?) : ResponseDTO() {
    override val name = "replace_if_lower"
}

@Serializable
data class UpdateVehicleByIdResponse(override val error: String?) : ResponseDTO() {
    override val name = "update"
}
