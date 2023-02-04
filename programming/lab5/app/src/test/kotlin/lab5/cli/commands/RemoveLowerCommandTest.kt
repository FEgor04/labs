package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.verify
import lab5.cli.utils.ReaderUtils
import lab5.entities.vehicle.VehicleFactory
import org.junit.jupiter.api.Test

class RemoveLowerCommandTest : CommandTest() {
    @Test
    fun ok() {
        mockkObject(ReaderUtils)
        val vehicle = VehicleFactory.generateRandomVehicle()
        val cmd = RemoveLowerCommand(repository)
        every { ReaderUtils.readVehicle(writer, reader) } returns vehicle
        every { repository.removeLower(vehicle) } returns Unit
        cmd.handle("remove_lower", writer, reader)
        verify {
            repository.removeLower(vehicle)
            writer.write("Успех!\n")
            writer.flush()
        }
        confirmVerified(writer)
        confirmVerified(repository)
    }
}