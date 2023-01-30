package lab5.entities.vehicle

import lab5.entities.FactoryException
import lab5.entities.ValidationException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class VehicleFactoryTest {
    private val factory = VehicleFactory

    @Test
    fun `Test create from string (good vehicle)`() {
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
            assertEquals(veh, newVeh.copy(id=veh.id, creationDate = veh.creationDate))
        }
        catch(e: ValidationException) {
            println(e)
            throw e
        }
    }

    @Test
    fun `Test create from string (bad x)`() {
        val veh = factory.generateRandomVehicle()
        assertThrows<FactoryException> {
            factory.createVehicleFromString(
                name=veh.name,
                xStr = "It's not integer, lol",
                yStr = veh.coordinates.y.toString(),
                enginePowerStr = veh.enginePower.toString(),
                fuelType = veh.fuelType.toString(),
                vehicleType = veh.type.toString()
            )
        }
    }
    @Test
    fun `Test create from string (bad y)`() {
        val veh = factory.generateRandomVehicle()
        assertThrows<FactoryException> {
            factory.createVehicleFromString(
                name=veh.name,
                xStr = veh.coordinates.x.toString(),
                yStr = "It's not integer, lol",
                enginePowerStr = veh.enginePower.toString(),
                fuelType = veh.fuelType.toString(),
                vehicleType = veh.type.toString()
            )
        }
    }

    @Test
    fun `Test create from string (bad engine power)`() {
        val veh = factory.generateRandomVehicle()
        assertThrows<FactoryException> {
            factory.createVehicleFromString(
                name=veh.name,
                xStr = veh.coordinates.x.toString(),
                yStr = veh.coordinates.y.toString(),
                enginePowerStr = "Its not double",
                fuelType = veh.fuelType.toString(),
                vehicleType = veh.type.toString()
            )
        }
    }

    @Test
    fun `Test create from string (bad fuel type)`() {
        val veh = factory.generateRandomVehicle()
        assertThrows<FactoryException> {
            factory.createVehicleFromString(
                name=veh.name,
                xStr = veh.coordinates.x.toString(),
                yStr = veh.coordinates.y.toString(),
                enginePowerStr = veh.enginePower.toString(),
                fuelType = "Not an enum element",
                vehicleType = veh.type.toString()
            )
        }
    }

    @Test
    fun generateRandomVehicle() {
        val vehicle = factory.generateRandomVehicle()
        vehicle.validate()
    }
}