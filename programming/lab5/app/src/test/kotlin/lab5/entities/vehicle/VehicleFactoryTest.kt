package lab5.entities.vehicle

import io.mockk.InternalPlatformDsl.toStr
import lab5.entities.FactoryException
import lab5.entities.ValidationException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows

class VehicleFactoryTest {
    private val factory = VehicleFactory


    @Nested
    inner class CreateFromString {
        @Test
        fun `good vehicle`() {
            val veh = factory.generateRandomVehicle()
            try {
                val newVeh = factory.createVehicleFromString(
                    name = veh.name,
                    xStr = veh.coordinates.x.toString() + "     ",
                    yStr = "     " + veh.coordinates.y.toString(),
                    enginePowerStr = veh.enginePower.toString() + "     ",
                    fuelType = "    " + veh.fuelType.toString().lowercase() + "    ",
                    vehicleType = veh.type.toString()
                )
                assertEquals(veh, newVeh.copy(id = veh.id, creationDate = veh.creationDate))
            } catch (e: ValidationException) {
                throw e
            }
        }

        @Test
        fun `bad x`() {
            val veh = factory.generateRandomVehicle()
            assertThrows<FactoryException> {
                factory.createVehicleFromString(
                    name = veh.name,
                    xStr = "It's not integer, lol",
                    yStr = veh.coordinates.y.toString(),
                    enginePowerStr = veh.enginePower.toString(),
                    fuelType = veh.fuelType.toString(),
                    vehicleType = veh.type.toString()
                )
            }
        }

        @Test
        fun `bad y`() {
            val veh = factory.generateRandomVehicle()
            assertThrows<FactoryException> {
                factory.createVehicleFromString(
                    name = veh.name,
                    xStr = veh.coordinates.x.toString(),
                    yStr = "It's not integer, lol",
                    enginePowerStr = veh.enginePower.toString(),
                    fuelType = veh.fuelType.toString(),
                    vehicleType = veh.type.toString()
                )
            }
        }

        @Test
        fun `bad engine power`() {
            val veh = factory.generateRandomVehicle()
            assertThrows<FactoryException> {
                factory.createVehicleFromString(
                    name = veh.name,
                    xStr = veh.coordinates.x.toString(),
                    yStr = veh.coordinates.y.toString(),
                    enginePowerStr = "Its not double",
                    fuelType = veh.fuelType.toString(),
                    vehicleType = veh.type.toString()
                )
            }
        }

        @Test
        fun `bad fuel type`() {
            val veh = factory.generateRandomVehicle()
            assertThrows<FactoryException> {
                factory.createVehicleFromString(
                    name = veh.name,
                    xStr = veh.coordinates.x.toString(),
                    yStr = veh.coordinates.y.toString(),
                    enginePowerStr = veh.enginePower.toString(),
                    fuelType = "Not an enum element",
                    vehicleType = veh.type.toString()
                )
            }
        }

        @Test
        fun `bad vehicle type`() {
            val veh = factory.generateRandomVehicle()
            assertThrows<FactoryException> {
                factory.createVehicleFromString(
                    name = veh.name,
                    xStr = veh.coordinates.x.toString(),
                    yStr = veh.coordinates.y.toString(),
                    enginePowerStr = veh.enginePower.toString(),
                    fuelType = veh.fuelType.toString(),
                    vehicleType = "not an enum element"
                )
            }
        }

        @Test fun `y is null`() {
            val veh = factory.generateRandomVehicle()
            val actual = factory.createVehicleFromString(
                name = veh.name,
                xStr = veh.coordinates.x.toString(),
                yStr = "null",
                enginePowerStr = veh.enginePower.toString(),
                fuelType = veh.fuelType.toStr(),
                vehicleType = veh.type.toString()
            )
            assertEquals(veh.copy(coordinates = veh.coordinates.copy(y=null)), actual.copy(id=veh.id))
        }


        @Test fun `y is empty`() {
            val veh = factory.generateRandomVehicle()
            val actual = factory.createVehicleFromString(
                name = veh.name,
                xStr = veh.coordinates.x.toString(),
                yStr = "",
                enginePowerStr = veh.enginePower.toString(),
                fuelType = veh.fuelType.toStr(),
                vehicleType = veh.type.toString()
            )
            assertEquals(veh.copy(coordinates = veh.coordinates.copy(y=null)), actual.copy(id=veh.id))
        }

        @Test fun `vehicleType is null`() {
            val veh = factory.generateRandomVehicle().copy(type=null)
            val actual = factory.createVehicleFromString(
                name = veh.name,
                xStr = veh.coordinates.x.toString(),
                yStr = veh.coordinates.y.toString(),
                enginePowerStr = veh.enginePower.toString(),
                fuelType = veh.fuelType.toString(),
                vehicleType = "null"
            )
            assertEquals(veh, actual.copy(id=veh.id))
        }


        @Test fun `vehicleType is empty`() {
            val veh = factory.generateRandomVehicle().copy(type=null)
            val actual = factory.createVehicleFromString(
                name = veh.name,
                xStr = veh.coordinates.x.toString(),
                yStr = veh.coordinates.y.toString(),
                enginePowerStr = veh.enginePower.toString(),
                fuelType = veh.fuelType.toString(),
                vehicleType = ""
            )
            assertEquals(veh, actual.copy(id=veh.id))
        }

        @Test fun `name is empty`() {
            val veh = factory.generateRandomVehicle().copy(type=null)

            assertThrows<FactoryException> {
                factory.createVehicleFromString(
                    name = "",
                    xStr = veh.coordinates.x.toString(),
                    yStr = veh.coordinates.y.toString(),
                    enginePowerStr = veh.enginePower.toString(),
                    fuelType = veh.fuelType.toString(),
                    vehicleType = veh.type.toString(),
                )
            }
        }


        @Test fun `x is invalid`() {
            val veh = factory.generateRandomVehicle().copy(type=null)

            assertThrows<ValidationException> {
                factory.createVehicleFromString(
                    name = "valid",
                    xStr = "-5000",
                    yStr = veh.coordinates.y.toString(),
                    enginePowerStr = veh.enginePower.toString(),
                    fuelType = veh.fuelType.toString(),
                    vehicleType = veh.type.toString(),
                )
            }
        }
    }

    @Nested
    inner class GenerateRandomVehicle {
        @Test
        fun `should be ok`() {
            val vehicle = factory.generateRandomVehicle()
            vehicle.validate()
        }
    }
}