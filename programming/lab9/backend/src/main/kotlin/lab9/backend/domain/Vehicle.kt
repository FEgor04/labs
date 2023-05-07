package lab9.backend.domain

import java.time.LocalDate

data class Vehicle private constructor(
        val id: VehicleID,
        val name: String,
        val creatorID: User.UserID,
        val coordinates: Coordinates,
        val creationDate: LocalDate,
        val enginePower: Double,
        val vehicleType: VehicleType,
        val fuelType: FuelType?,
) {
    enum class FuelType {
        GASOLINE,
        ELECTRICITY,
        MANPOWER,
        PLASMA,
        ANTIMATTER
    }

    enum class VehicleType {
        PLANE,
        SUBMARINE,
        BOAT,
        BICYCLE;
    }

    companion object {
        fun withID(
                id: VehicleID,
                name: String,
                creatorID: User.UserID,
                coordinates: Coordinates,
                creationDate: LocalDate,
                enginePower: Double,
                vehicleType: VehicleType,
                fuelType: FuelType?,
        ): Vehicle = Vehicle(id, name, creatorID, coordinates, creationDate, enginePower, vehicleType, fuelType)

        fun withoutID(
                name: String,
                creatorID: User.UserID,
                coordinates: Coordinates,
                creationDate: LocalDate,
                enginePower: Double,
                vehicleType: VehicleType,
                fuelType: FuelType?,
        ): Vehicle =
                Vehicle(VehicleID.NoID, name, creatorID, coordinates, creationDate, enginePower, vehicleType, fuelType)
    }


    data class Coordinates(val x: Int, val y: Long?)

    data class VehicleID(val id: Int) {
        companion object {
            val NoID = VehicleID(-1)
        }
    }
}
