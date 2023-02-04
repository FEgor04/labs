package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.verify
import lab5.cli.utils.ReaderUtils
import lab5.entities.vehicle.VehicleFactory
import lab5.repositories.ReplaceIfLowerResults
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ReplaceIfLowerCommandTest : CommandTest() {
    @Test fun `replaced`() {
        mockkObject(ReaderUtils)
        val old = VehicleFactory.generateRandomVehicle()
        val new = old.copy(enginePower = old.enginePower/2)
        val cmd = ReplaceIfLowerCommand(repository)

        every { ReaderUtils.readVehicle(writer, reader) } returns new
        every { repository.getVehicleById(old.id) }
        every { repository.replaceIfLower(old.id, new) } returns ReplaceIfLowerResults.REPLACED

        cmd.handle("replace_if_lower ${old.id}", writer, reader)

        verify {
            writer.write("Успешно заменено.\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test fun `not replaced`() {
        mockkObject(ReaderUtils)
        val old = VehicleFactory.generateRandomVehicle()
        val new = old.copy(enginePower = old.enginePower/2)
        val cmd = ReplaceIfLowerCommand(repository)

        every { ReaderUtils.readVehicle(writer, reader) } returns new
        every { repository.replaceIfLower(old.id, new) } returns ReplaceIfLowerResults.NOT_REPLACED

        cmd.handle("replace_if_lower ${old.id}", writer, reader)

        verify {
            writer.write("Элемент по ключу меньше данного. Не заменено.\n")
            writer.flush()
        }
        verify {
            ReaderUtils.readVehicle(writer, reader)
        }
        verify {
            repository.replaceIfLower(old.id, new)
        }
        confirmVerified(writer)
        confirmVerified(ReaderUtils)
        confirmVerified(repository)
    }

    @Test fun `not exists`() {
        mockkObject(ReaderUtils)
        val old = VehicleFactory.generateRandomVehicle()
        val new = old.copy(enginePower = old.enginePower/2)
        val cmd = ReplaceIfLowerCommand(repository)

        every { ReaderUtils.readVehicle(writer, reader) } returns new
        every { repository.replaceIfLower(old.id, new) } returns ReplaceIfLowerResults.NOT_EXISTS

        cmd.handle("replace_if_lower ${old.id}", writer, reader)

        verify {
            writer.write("Такого ключа не существует. Вставлен новый элемент.\n")
            writer.flush()
        }
        verify {
            ReaderUtils.readVehicle(writer, reader)
        }
        verify {
            repository.replaceIfLower(old.id, new)
        }
        confirmVerified(writer)
        confirmVerified(ReaderUtils)
        confirmVerified(repository)
    }

    @Test fun `bad input`() {
        val cmd = ReplaceIfLowerCommand(repository)
        cmd.handle("replace_if_lower", writer, reader)
        verify {
            writer.write("Некорректный ввод.\n")
            writer.flush()
        }
        confirmVerified(writer)
    }
}