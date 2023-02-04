package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import lab5.entities.vehicle.VehicleType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

class CountByTypeCommandTest : CommandTest() {
    companion object {
        @JvmStatic
        fun types() = VehicleType.values().toList()
    }

    @MethodSource("types")
    @ParameterizedTest fun execute(type: VehicleType) {
        val cmd = CountByTypeCommand(repository)

        every { repository.countByType(type) } returns 20
        cmd.handle("count_by_type ${type.toString()}", reader = reader, writer=writer)
        verify {
            writer.write("В коллекции содержиться 20 элементов с типом $type.\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test fun nullType() {
        val cmd = CountByTypeCommand(repository)

        every { repository.countByType(null) } returns 20
        cmd.handle("count_by_type", reader = reader, writer=writer)
        verify {
            writer.write("В коллекции содержиться 20 элементов с типом null.\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test fun `not an enum element`() {
        val cmd = CountByTypeCommand(repository)

        cmd.handle("count_by_type asdsaada", reader = reader, writer=writer)
        verify {
            writer.write("Некорректный ввод. Допустимые значения type: ${VehicleType.values().map { it.toString() }} или пустая строка для null\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test fun `not a word`() {
        val cmd = CountByTypeCommand(repository)

        cmd.handle("count_by_type 3123213", reader = reader, writer=writer)
        verify {
            writer.write("Некорректный ввод. Допустимые значения type: ${VehicleType.values().map { it.toString() }} или пустая строка для null\n")
            writer.flush()
        }
        confirmVerified(writer)
    }
}