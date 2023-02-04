package lab5.entities.vehicle

import lab5.entities.ValidationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class VehicleTest {

    @Test
    fun `Should be OK`() {
        val vehicle = Vehicle(
            id=512,
            name="Test",
            coordinates = Coordinates(512, 1512),
            enginePower = 5.2,
            type = VehicleType.BICYCLE,
            fuelType = FuelType.ANTIMATTER,
            creationDate = java.time.LocalDate.now()
        )
        vehicle.validate()
    }

    @Test fun `Empty name`() {
        val vehicle = Vehicle(
            id=1,
            name="",
            coordinates = Coordinates(512, 1512),
            enginePower = 5.2,
            type = VehicleType.BICYCLE,
            fuelType = FuelType.ANTIMATTER,
            creationDate = java.time.LocalDate.now()
        )
        assertThrows<ValidationException> {
            vehicle.validate()
        }
    }

    @Test fun `Bad ID`() {
        val vehicle = Vehicle(
            id=0,
            name="Test",
            coordinates = Coordinates(512, 1512),
            enginePower = 5.2,
            type = VehicleType.BICYCLE,
            fuelType = FuelType.ANTIMATTER,
            creationDate = java.time.LocalDate.now()
        )
        assertThrows<ValidationException> {
            vehicle.validate()
        }
    }

    @Test fun `Bad Coordinates`() {
        val vehicle = Vehicle(
            id=0,
            name="Test",
            coordinates = Coordinates(-150, 1512),
            enginePower = 5.2,
            type = VehicleType.BICYCLE,
            fuelType = FuelType.ANTIMATTER,
            creationDate = java.time.LocalDate.now()
        )
        assertThrows<ValidationException> {
            vehicle.validate()
        }
    }

    @Test fun `Bad Name`() {
        val vehicle = Vehicle(
            id=0,
            name="",
            coordinates = Coordinates(-150, 1512),
            enginePower = 5.2,
            type = VehicleType.BICYCLE,
            fuelType = FuelType.ANTIMATTER,
            creationDate = java.time.LocalDate.now()
        )
        assertThrows<ValidationException> {
            vehicle.validate()
        }
    }

    @Test fun `Bad Engine Power`() {
        val vehicle = Vehicle(
            id=0,
            name="Test",
            coordinates = Coordinates(-150, 1512),
            enginePower = -10.0,
            type = VehicleType.BICYCLE,
            fuelType = FuelType.ANTIMATTER,
            creationDate = java.time.LocalDate.now()
        )
        assertThrows<ValidationException> {
            vehicle.validate()
        }
    }

}