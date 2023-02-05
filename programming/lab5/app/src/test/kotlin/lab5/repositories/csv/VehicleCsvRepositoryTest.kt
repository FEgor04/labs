package lab5.repositories.csv

import lab5.entities.ValidationException
import lab5.entities.vehicle.Vehicle
import lab5.entities.vehicle.VehicleType
import lab5.repositories.ReplaceIfLowerResults
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.io.File

/**
 * С такой аннотацией Repository создается единожды, и потом очищается
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehicleCsvRepositoryTest {
    private var repository = VehicleCsvRepository(File(""))

    @AfterEach
    fun tearDown() {
        repository.clear()
    }

    @Nested
    inner class InsertVehicle {
        @Test
        fun ok() {
            val vehicle = Vehicle.generateRandomVehicle()
            repository.insertVehicle(vehicle)
            assertEquals(vehicle.copy(id = 1), repository.getVehicleById(1), "elements should be equal")
            assertEquals(1, repository.getCollectionInfo().elementsCount)
        }

        @Test
        fun `invalid vehicle`() {
            val vehicle = Vehicle.generateRandomVehicle().copy(enginePower = -10.0)
            assertThrows<ValidationException> {
                repository.insertVehicle(vehicle)
            }
            assertEquals(null, repository.getVehicleById(1))
            assertEquals(0, repository.getCollectionInfo().elementsCount)
        }
    }

    @Nested
    inner class GetVehicleById {
        @Test
        fun ok() {
            val vehicle = Vehicle.generateRandomVehicle().copy(id = 1)
            repository.insertVehicle(vehicle)
            val actualVehicle = repository.getVehicleById(1)
            assertEquals(vehicle, actualVehicle)
        }

        @Test
        fun `no vehicle`() {
            val actualVehicle = repository.getVehicleById(1)
            assertEquals(null, actualVehicle)
        }
    }

    @Nested
    inner class RemoveVehicle {
        @Test
        fun `insert and remove`() {
            val vehicle = Vehicle.generateRandomVehicle()
            repository.insertVehicle(vehicle)
            assertEquals(1, repository.getCollectionInfo().elementsCount)
            repository.removeVehicle(1)
            assertEquals(0, repository.getCollectionInfo().elementsCount)
        }

        @Test
        fun `remove non-existent`() {
            assertEquals(0, repository.getCollectionInfo().elementsCount)
            repository.removeVehicle(1)
            assertEquals(0, repository.getCollectionInfo().elementsCount)
        }
    }

    @Nested
    inner class UpdateVehicleById {
        @Test
        fun ok() {
            val vehicle = Vehicle.generateRandomVehicle()
            repository.insertVehicle(vehicle)
            assertEquals(1, repository.getCollectionInfo().elementsCount)
            val newVehicle = Vehicle.generateRandomVehicle().copy(id = vehicle.id)
            repository.updateVehicleById(newVehicle)
            assertEquals(newVehicle, repository.getVehicleById(vehicle.id))
        }

        @Test
        fun `update non-existent`() {
            val vehicle = Vehicle.generateRandomVehicle()
            repository.insertVehicle(vehicle)
            assertEquals(1, repository.getCollectionInfo().elementsCount)
            val newVehicle = Vehicle.generateRandomVehicle().copy(id = vehicle.id)
            repository.updateVehicleById(newVehicle)
            assertEquals(newVehicle, repository.getVehicleById(vehicle.id))
        }
    }

    @Nested
    inner class ListAllVehicles {
        @Test
        fun ok() {
            val vehicles = (1..100).map { Vehicle.generateRandomVehicle().copy(id = it) }
            vehicles.forEach { repository.insertVehicle(it) }
            val actual = repository.listAllVehicles().toList()
            actual.forEachIndexed { id, it -> assertEquals(vehicles[id], it) }
        }

        @Test
        fun `empty collection`() {
            val actual = repository.listAllVehicles().toList()
            assert(actual.isEmpty())
        }
    }

    @Nested
    inner class RemoveGreater {
        @Test
        fun ok() {
            val vehicles =
                (1..100).map { Vehicle.generateRandomVehicle().copy(id = it, enginePower = it.toDouble()) }
            vehicles.forEach { repository.insertVehicle(it) }
            val actual = repository.listAllVehicles().toList()
            actual.forEachIndexed { id, it -> assertEquals(vehicles[id], it) }
            val power = 50.0
            repository.removeGreater(Vehicle.generateRandomVehicle().copy(enginePower = power))

            repository.forEach { assert(it.enginePower <= power) }
        }

        @Test
        fun `remove all`() {
            val vehicles =
                (1..100).map { Vehicle.generateRandomVehicle().copy(id = it, enginePower = it.toDouble()) }
            vehicles.forEach { repository.insertVehicle(it) }
            val actual = repository.listAllVehicles().toList()
            actual.forEachIndexed { id, it -> assertEquals(vehicles[id], it) }
            val power = -100.0
            repository.removeGreater(Vehicle.generateRandomVehicle().copy(enginePower = power))

            assertEquals(0, repository.getCollectionInfo().elementsCount)
        }

        @Test
        fun `do not remove`() {
            val vehicles =
                (1..100).map { Vehicle.generateRandomVehicle().copy(id = it, enginePower = it.toDouble()) }
            vehicles.forEach { repository.insertVehicle(it) }
            val actual = repository.listAllVehicles().toList()
            actual.forEachIndexed { id, it -> assertEquals(vehicles[id], it) }
            val power = 500.0
            repository.removeGreater(Vehicle.generateRandomVehicle().copy(enginePower = power))

            assertEquals(100, repository.getCollectionInfo().elementsCount)
        }
    }

    @Nested
    inner class RemoveLower {
        @Test
        fun ok() {
            val vehicles =
                (1..100).map { Vehicle.generateRandomVehicle().copy(id = it, enginePower = it.toDouble()) }
            vehicles.forEach { repository.insertVehicle(it) }
            val actual = repository.listAllVehicles().toList()
            actual.forEachIndexed { id, it -> assertEquals(vehicles[id], it) }
            val power = 50.0
            repository.removeLower(Vehicle.generateRandomVehicle().copy(enginePower = power))

            repository.forEach { assert(it.enginePower >= power) }
        }

        @Test
        fun `remove all`() {
            val vehicles =
                (1..100).map { Vehicle.generateRandomVehicle().copy(id = it, enginePower = it.toDouble()) }
            vehicles.forEach { repository.insertVehicle(it) }
            val actual = repository.listAllVehicles().toList()
            actual.forEachIndexed { id, it -> assertEquals(vehicles[id], it) }
            val power = 500.0
            repository.removeLower(Vehicle.generateRandomVehicle().copy(enginePower = power))

            assertEquals(0, repository.getCollectionInfo().elementsCount)
        }

        @Test
        fun `do not remove`() {
            val vehicles =
                (1..100).map { Vehicle.generateRandomVehicle().copy(id = it, enginePower = it.toDouble()) }
            vehicles.forEach { repository.insertVehicle(it) }
            val actual = repository.listAllVehicles().toList()
            actual.forEachIndexed { id, it -> assertEquals(vehicles[id], it) }
            val power = -500.0
            repository.removeLower(Vehicle.generateRandomVehicle().copy(enginePower = power))

            assertEquals(100, repository.getCollectionInfo().elementsCount)
        }
    }

    @Nested
    inner class ReplaceIfLower {
        @Test
        fun replace() {
            val old = Vehicle.generateRandomVehicle().copy(enginePower = 50.0)
            repository.insertVehicle(old)
            val new = Vehicle.generateRandomVehicle().copy(enginePower = 25.0)
            val result = repository.replaceIfLower(1, new)
            assertEquals(ReplaceIfLowerResults.REPLACED, result)
            assertEquals(new.copy(id = 1), repository.getVehicleById(1))
        }

        @Test
        fun `no replace`() {
            val old = Vehicle.generateRandomVehicle().copy(enginePower = 25.0)
            repository.insertVehicle(old)
            val new = Vehicle.generateRandomVehicle().copy(enginePower = 50.0)
            val result = repository.replaceIfLower(1, new)
            assertEquals(ReplaceIfLowerResults.NOT_REPLACED, result)
            assertEquals(old.copy(id = 1), repository.getVehicleById(1))
        }

        @Test
        fun `does not exists`() {
            val new = Vehicle.generateRandomVehicle().copy(enginePower = 50.0)
            val result = repository.replaceIfLower(1, new)
            assertEquals(ReplaceIfLowerResults.NOT_EXISTS, result)
        }
    }

    @Nested
    inner class GetMinById {
        @Test
        fun `many elements`() {
            val vehicles =
                (1..100).map { Vehicle.generateRandomVehicle().copy(id = it, enginePower = it * 10.0) }
            vehicles.forEach { repository.insertVehicle(it) }
            val min = repository.getMinById()
            assertNotNull(min)
            val actual = repository.getVehicleById(1)
            assertEquals(actual, min)
        }

        @Test
        fun `no element with id #1`() {
            val vehicles =
                (1..100).map { Vehicle.generateRandomVehicle().copy(id = it, enginePower = it * 10.0) }
            vehicles.forEach { repository.insertVehicle(it) }
            (1..25).forEach { repository.removeVehicle(it) }
            val min = repository.getMinById()
            assertNotNull(min)
            val actual = repository.getVehicleById(26)
            assertEquals(actual, min)
        }

        @Test
        fun `empty collection`() {
            assertNull(repository.getMinById())
        }
    }

    @Nested
    inner class CountByType {
        @Test
        fun `20 types each`() {
            val typesCount = VehicleType.values().size + 1 // +1 для null
            (1..typesCount * 10).forEach {
                repository.insertVehicle(
                    Vehicle.generateRandomVehicle()
                        .copy(
                            type = if (it % typesCount == typesCount - 1) {
                                null
                            } else {
                                VehicleType.values()[it % typesCount]
                            }
                        )
                )
            }
            VehicleType.values().forEach { assertEquals(10, repository.countByType(it)) }
            assertEquals(10, repository.countByType(null))
        }

        @Test
        fun `no type`() {
            repeat(100) { repository.insertVehicle(Vehicle.generateRandomVehicle()) }
            assertEquals(0, repository.countByType(null))
        }

        @Test
        fun `no elements`() {
            assertEquals(0, repository.countByType(null))
            VehicleType.values().forEach { assertEquals(0, repository.countByType(it)) }
        }
    }

    @Nested
    inner class CountLessThanEnginePower {
        @Test
        fun `no elements`() {
            (1..100).forEach {
                assertEquals(0, repository.countLessThanEnginePower(it * 1.1))
            }
        }

        @Test
        fun `equal engine power`() {
            repeat(100) {
                repository.insertVehicle(Vehicle.generateRandomVehicle().copy(enginePower = 1.0))
            }
            assertEquals(100, repository.countLessThanEnginePower(1.1))
            assertEquals(0, repository.countLessThanEnginePower(1.0))
            assertEquals(0, repository.countLessThanEnginePower(0.0))
        }

        @Test
        fun ok() {
            (1..100).forEach {
                repository.insertVehicle(Vehicle.generateRandomVehicle().copy(enginePower = it.toDouble()))
            }
            (1..100).forEach {
                assertEquals(it - 1, repository.countLessThanEnginePower(it.toDouble() - 0.1))
                assertEquals(it - 1, repository.countLessThanEnginePower(it.toDouble()))
                assertEquals(it, repository.countLessThanEnginePower(it.toDouble() + 1))
            }
        }
    }

    @Nested
    inner class LoadOneLine {
        @Test
        fun ok() {
            val vehicle = Vehicle.generateRandomVehicle()
            val method = repository.javaClass.getDeclaredMethod(
                "loadOneLine",
                String::class.java
            ) // очень плохо, но нужно же как-то тестить приватные функции
            method.isAccessible = true
            method.invoke(repository, vehicle.toCsv())
            assertEquals(vehicle, repository.getVehicleById(vehicle.id))
        }

        @Test
        fun noCsv() {
            val vehicle = Vehicle.generateRandomVehicle()
            val method = repository.javaClass.getDeclaredMethod(
                "loadOneLine",
                String::class.java
            ) // очень плохо, но нужно же как-то тестить приватные функции
            method.isAccessible = true
            assertThrows<Exception> {
                method.invoke(
                    repository,
                    "Подходит как то Петька к Василь Иванычу и сдает лабу по нюансам"
                )
            }
        }
    }
}