package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.verify
import lab5.cli.utils.ReaderUtils
import lab5.entities.vehicle.Vehicle
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RemoveGreaterCommandTest : CommandTest() {
    @Test
    fun ok() {
        mockkObject(ReaderUtils)
        val vehicle = Vehicle.generateRandomVehicle()
        val cmd = RemoveGreaterCommand(repository)
        every { ReaderUtils.readVehicle(writer, reader) } returns vehicle
        every { repository.removeGreater(vehicle) } returns Unit
        cmd.handle("remove_greater", writer, reader)
        verify {
            repository.removeGreater(vehicle)
            writer.write("Успех!\n")
            writer.flush()
        }
        confirmVerified(writer)
        confirmVerified(repository)
    }
}