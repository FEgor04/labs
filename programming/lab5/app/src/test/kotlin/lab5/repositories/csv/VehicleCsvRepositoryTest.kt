package lab5.repositories.csv

import lab5.entities.ValidationException
import lab5.entities.vehicle.VehicleFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class VehicleCsvRepositoryTest {
    private var repository = VehicleCsvRepository("")

    @AfterEach
    fun tearDown() {
        repository.clear()
    }

    @Test
    fun insertVehicle() {
        val vehicle = VehicleFactory.generateRandomVehicle()
        repository.insertVehicle(vehicle)
        assertEquals(1, repository.getCollectionInfo().elementsCount)
    }

    @Test
    fun insertInvalidVehicle() {
        val vehicle = VehicleFactory.generateRandomVehicle().copy(enginePower = -10.0)
        assertThrows<ValidationException> {
            repository.insertVehicle(vehicle)
        }
    }

    @Test
    fun getVehicleById() {
        val vehicle = VehicleFactory.generateRandomVehicle().copy(id=1)
        repository.insertVehicle(vehicle)
        val actualVehicle = repository.getVehicleById(1)
        assertEquals(vehicle, actualVehicle)
    }

    @Test
    fun updateVehicleById() {
    }

    @Test
    fun listAllVehicles() {
    }

    @Test
    fun removeVehicle() {
        val vehicle = VehicleFactory.generateRandomVehicle()
        repository.insertVehicle(vehicle)
        assertEquals(1, repository.getCollectionInfo().elementsCount)
        repository.removeVehicle(1)
        assertEquals(0, repository.getCollectionInfo().elementsCount)
    }

    @Test
    fun saveCollection() {
    }

    @Test
    fun getCollectionInfo() {
    }
}