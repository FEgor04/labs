package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import lab5.entities.vehicle.VehicleType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class CountByTypeCommandTest : CommandTest() {
    val cmd = CountByTypeCommand(repository)

    companion object {
        @JvmStatic
        fun types() = VehicleType.values().toList()
    }

    @MethodSource("types")
    @ParameterizedTest
    fun execute(type: VehicleType) {

        every { repository.countByType(type) } returns 20
        cmd.handle("count_by_type $type", reader = reader, writer = writer)
        verify {
            writer.write("В коллекции 20 элементов с типом $type.\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test
    fun nullType() {
        every { repository.countByType(null) } returns 20
        cmd.handle("count_by_type", reader = reader, writer = writer)
        verify {
            writer.write("В коллекции 20 элементов с типом null.\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test
    fun `not an enum element`() {
        cmd.handle("count_by_type asdsaada", reader = reader, writer = writer)
        verify {
            writer.write(
                "Некорректный ввод. Допустимые значения type: ${
                    VehicleType.values().map { it.toString() }
                } или пустая строка для null\n"
            )
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test
    fun `not a word`() {
        cmd.handle("count_by_type 3123213", reader = reader, writer = writer)
        verify {
            writer.write(
                "Некорректный ввод. Допустимые значения type: ${
                    VehicleType.values().map { it.toString() }
                } или пустая строка для null\n"
            )
            writer.flush()
        }
        confirmVerified(writer)
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    inner class TestCheck {
        @MethodSource("types")
        @ParameterizedTest
        fun `should be ok`(type: VehicleType) {
            assert(cmd.check("count_by_type $type"))
            assert(cmd.check("    count_by_type $type    "))
            assert(cmd.check("    count_by_type         $type    "))
            assert(cmd.check("    count_by_type           $type     "))
            assert(cmd.check("count_by_type    ${type}dasdsa"))
            assert(cmd.check("count_by_type    dasdsada${type}dasdsa"))
            assert(cmd.check("count_by_type    dasdsada${type}"))
        }

        fun types() = VehicleType.values().toList()
    }
}