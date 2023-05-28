package lab9.backend.adapter.out.persistence.vehicle

import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Root
import lab9.backend.domain.Vehicle
import org.springframework.stereotype.Component

@Component
class VehicleFieldResolver {
    fun toPathName(field: Vehicle.Field): String {
        return when(field) {
            Vehicle.Field.ID -> "id"
            Vehicle.Field.NAME -> "name"
            Vehicle.Field.X -> "x"
            Vehicle.Field.Y -> "y"
            Vehicle.Field.CREATION_DATE -> "creationDate"
            Vehicle.Field.ENGINE_POWER -> "enginePower"
            Vehicle.Field.VEHICLE_TYPE -> "vehicleType"
            Vehicle.Field.FUEL_TYPE -> "fuelType"
            Vehicle.Field.CREATOR_ID -> "creator"
        }
    }
}