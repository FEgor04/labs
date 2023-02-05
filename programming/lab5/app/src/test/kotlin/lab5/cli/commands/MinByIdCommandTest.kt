package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import lab5.entities.vehicle.Vehicle
import org.junit.jupiter.api.Test

class MinByIdCommandTest : CommandTest() {
    @Test
    fun `no elements`() {
        val cmd = MinByIdCommand(repository)
        every { repository.getMinById() } returns null
        cmd.handle("    min_by_id   ", writer, reader)
        verify {
            writer.write("В коллекции нет элементов.\n")
            writer.flush()
        }
        verify {
            repository.getMinById()
        }
        confirmVerified(writer)
        confirmVerified(repository)
    }

    @Test
    fun `has an element`() {
        val veh = Vehicle.generateRandomVehicle()
        val cmd = MinByIdCommand(repository)
        every { repository.getMinById() } returns veh
        cmd.handle("    min_by_id   ", writer, reader)
        verify {
            writer.write("$veh\n")
            writer.flush()
        }
        verify {
            repository.getMinById()
        }
        confirmVerified(writer)
        confirmVerified(repository)
    }
}