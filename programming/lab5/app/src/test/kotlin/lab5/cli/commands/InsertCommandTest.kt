package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.verify
import lab5.cli.utils.ReaderUtils
import lab5.entities.vehicle.Vehicle
import org.junit.jupiter.api.Test

class InsertCommandTest : CommandTest() {
    @Test
    fun ok() {
        mockkObject(ReaderUtils)
        val veh = Vehicle.generateRandomVehicle()
        val cmd = InsertCommand(repository)

        every { ReaderUtils.readVehicle(writer, reader) } returns veh
        every { repository.insertVehicle(veh) } returns Unit

        cmd.handle("insert", writer, reader)

        verify {
            writer.write("Успех!\n")
            writer.flush()
        }
        verify {
            ReaderUtils.readVehicle(writer, reader)
        }
        verify {
            repository.insertVehicle(veh)
        }
        confirmVerified(ReaderUtils)
        confirmVerified(repository)
        confirmVerified(writer)
    }
}