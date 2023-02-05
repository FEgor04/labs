package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import lab5.entities.vehicle.Vehicle
import lab5.repositories.CollectionInfo
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ShowCommandTest : CommandTest() {
    @Test
    fun `got elements`() {
        val cmd = ShowCommand(repository)
        val vehicles = (1..100).map { Vehicle.generateRandomVehicle().copy(id = it) }
        val info =
            CollectionInfo(type = "Test collection", elementsCount = vehicles.size, initDate = LocalDateTime.now())
        every { repository.getCollectionInfo() } returns info
        every { repository.iterator() } returns vehicles.iterator()
        cmd.handle("show", writer, reader)
        vehicles.forEach {
            verify { writer.write("$it\n") }
        }
        verify {
            writer.flush()
        }
        verify {
            repository.getCollectionInfo()
            repository.iterator()
        }
        confirmVerified(writer)
        confirmVerified(repository)
    }

    @Test
    fun `no elements`() {
        val cmd = ShowCommand(repository)
        val info = CollectionInfo(type = "Test collection", elementsCount = 0, initDate = LocalDateTime.now())
        every { repository.getCollectionInfo() } returns info
        cmd.handle("show", writer, reader)
        verify {
            writer.write("В коллекции нет элементов\n")
            writer.flush()
        }
        verify {
            repository.getCollectionInfo()
        }
        confirmVerified(writer)
        confirmVerified(repository)
    }
}