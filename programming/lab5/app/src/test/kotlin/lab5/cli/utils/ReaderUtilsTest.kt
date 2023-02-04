package lab5.cli.utils

import io.mockk.*
import lab5.entities.vehicle.FuelType
import lab5.entities.vehicle.VehicleFactory
import lab5.entities.vehicle.VehicleType
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import java.io.BufferedReader
import java.io.BufferedWriter

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReaderUtilsTest {

    @Nested inner class ToEnumOrNullCaster {
        @Test fun ok() {
            VehicleType.values().forEach {
                assertEquals(it, ReaderUtils.toEnumOrNullCaster<VehicleType>(it.toString().uppercase()))
                assertEquals(it, ReaderUtils.toEnumOrNullCaster<VehicleType>(it.toString().lowercase()))
                assertEquals(it, ReaderUtils.toEnumOrNullCaster<VehicleType>(it.toString().lowercase() + "     "))
                assertEquals(it, ReaderUtils.toEnumOrNullCaster<VehicleType>("     $it"))
            }
        }

        @Test fun `null`() {
            assertEquals(null, ReaderUtils.toEnumOrNullCaster<VehicleType>(""))
            assertEquals(null, ReaderUtils.toEnumOrNullCaster<FuelType>(""))
        }

        @Test fun `bad input`() {
            assertThrows<IllegalArgumentException> {
                ReaderUtils.toEnumOrNullCaster<VehicleType>("obviously bad input")
            }
        }
    }

    @Nested inner class ToEnumCaster {
        @Test fun ok() {
            VehicleType.values().forEach {
                assertEquals(it, ReaderUtils.toEnumCaster<VehicleType>(it.toString().uppercase()))
                assertEquals(it, ReaderUtils.toEnumCaster<VehicleType>(it.toString().lowercase()))
                assertEquals(it, ReaderUtils.toEnumCaster<VehicleType>(it.toString().lowercase() + "     "))
                assertEquals(it, ReaderUtils.toEnumCaster<VehicleType>("     $it"))
            }
        }

        @Test fun `bad input`() {
            assertThrows<IllegalArgumentException> { ReaderUtils.toEnumCaster<VehicleType>("obviously bad input") }
            assertThrows<IllegalArgumentException> { ReaderUtils.toEnumCaster<VehicleType>("null") }
            assertThrows<IllegalArgumentException> { ReaderUtils.toEnumCaster<VehicleType>("") }
        }
    }

    @Nested inner class ToIntCaster {
        @Test fun ok() {
            (-1000..1000 step 100).forEach {
                assertEquals(it, ReaderUtils.toIntCaster(it.toString()))
                assertEquals(it, ReaderUtils.toIntCaster("$it      "))
                assertEquals(it, ReaderUtils.toIntCaster("     $it"))
            }
        }

        @Test fun `bad input`() {
            assertThrows<NumberFormatException> { ReaderUtils.toIntCaster("obviously bad input") }
            assertThrows<NumberFormatException> { ReaderUtils.toIntCaster("null") }
            assertThrows<NumberFormatException> { ReaderUtils.toIntCaster("") }
        }
    }

    @Nested inner class ToIntOrNullCaster {
        @Test fun ok() {
            (-1000..1000 step 100).forEach {
                assertEquals(it, ReaderUtils.toIntOrNullCaster(it.toString()))
                assertEquals(it, ReaderUtils.toIntOrNullCaster("$it      "))
                assertEquals(it, ReaderUtils.toIntOrNullCaster("     $it"))
            }
        }

        @Test fun `null`() {
            assertEquals(null, ReaderUtils.toIntOrNullCaster(""))
        }

        @Test fun `bad input`() {
            assertThrows<NumberFormatException> { ReaderUtils.toIntOrNullCaster("obviously bad input") }
            assertThrows<NumberFormatException> { ReaderUtils.toIntOrNullCaster("null") }
        }
    }

    @Nested inner class ToDoubleCaster {
        @Test fun ok() {
            (-10000..10000 step 25).forEach {
                assertEquals(it.toDouble()/10, ReaderUtils.toDoubleOrNullCaster((it.toDouble()/10).toString()))
                assertEquals(it.toDouble()/10, ReaderUtils.toDoubleOrNullCaster("${it.toDouble()/10}      "))
                assertEquals(it.toDouble()/10, ReaderUtils.toDoubleOrNullCaster("     ${it.toDouble()/10}"))
                assertEquals(it.toDouble(), ReaderUtils.toDoubleOrNullCaster("$it"))
                assertEquals(it.toDouble(), ReaderUtils.toDoubleOrNullCaster("$it      "))
                assertEquals(it.toDouble(), ReaderUtils.toDoubleOrNullCaster("     $it"))
            }
        }

        @Test fun `bad input`() {
            assertThrows<NumberFormatException> { ReaderUtils.toDoubleCaster("obviously bad input") }
            assertThrows<NumberFormatException> { ReaderUtils.toDoubleCaster("null") }

        }

        @Test fun `null`() {
            assertEquals(null, ReaderUtils.toDoubleOrNullCaster(""))
        }
    }

    @Nested inner class ToLongOrNullCaster {
        @Test fun ok() {
            (-1000..1000 step 100).forEach {
                assertEquals(it.toLong(), ReaderUtils.toLongOrNullCaster(it.toString()))
                assertEquals(it.toLong(), ReaderUtils.toLongOrNullCaster("$it      "))
                assertEquals(it.toLong(), ReaderUtils.toLongOrNullCaster("     $it"))
            }
        }

        @Test fun `null`() {
            assertEquals(null, ReaderUtils.toLongOrNullCaster(""))
        }

        @Test fun `bad input`() {
            assertThrows<NumberFormatException> { ReaderUtils.toLongOrNullCaster("obviously bad input") }
            assertThrows<NumberFormatException> { ReaderUtils.toLongOrNullCaster("null") }
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested inner class ReadType {
        val hint = "sample hint"
        val reader = mockk<BufferedReader>()
        val writer = mockk<BufferedWriter>()

        @AfterEach
        fun cleanUp() {
            clearAllMocks()
        }

        @Test fun readInt() {
            val predicate = {it: Int -> it > 50}

            every { reader.readLine() } returns "this is not an int!" andThen "-500" andThen "" andThen "100"
            every { writer.write(any<String>()) } returns Unit
            every { writer.flush() } returns Unit
            val value = ReaderUtils.readType<Int>(reader, writer, hint, ReaderUtils.toIntCaster, predicate)
            verify {
                writer.write(eq(hint))
                writer.flush()
                writer.write("Некорректный ввод числа. Попробуйте еще.\n")
                writer.flush()
                writer.write("Некорректный ввод. Попробуйте еще.\n")
                writer.flush()
                writer.write("Некорректный ввод числа. Попробуйте еще.\n")
                writer.flush()
            }
            confirmVerified(writer)
            assertEquals(100, value)

        }

        @Test fun readEnumOrNull() {
            every { reader.readLine() } returns "hey there" andThen "not an enum" andThen ""
            every { writer.write(any<String>()) } returns Unit
            every { writer.flush() } returns Unit
            val value = ReaderUtils.readType<VehicleType?>(reader, writer, hint, { ReaderUtils.toEnumOrNullCaster<VehicleType>(it) }, {true})
            verify {
                writer.write(hint)
                writer.flush()
                writer.write("Некорректный ввод enum.\n")
                writer.flush()
                writer.write("Некорректный ввод enum.\n")
                writer.flush()
            }
            confirmVerified(writer)
            assertEquals(null, value)
        }

        @Test fun readEnumOrNullNoErrors() {
            every { reader.readLine() } returns ""
            every { writer.write(any<String>()) } returns Unit
            every { writer.flush() } returns Unit
            val value = ReaderUtils.readType<VehicleType?>(reader, writer, hint, { ReaderUtils.toEnumOrNullCaster<VehicleType>(it) }, {true})
            verify {
                writer.write(hint)
                writer.flush()
            }
            confirmVerified(writer)
            assertEquals(null, value)
        }

        @Test fun readEnumNoError() {
            every { reader.readLine() } returns VehicleType.BICYCLE.toString()
            every { writer.write(any<String>()) } returns Unit
            every { writer.flush() } returns Unit
            val value = ReaderUtils.readType<VehicleType>(reader, writer, hint, { ReaderUtils.toEnumCaster<VehicleType>(it) }, {true})
            verify {
                writer.write(hint)
                writer.flush()
            }
            confirmVerified(writer)
            assertEquals(VehicleType.BICYCLE, value)
        }

        @Test fun readEnumOneError() {
            every { reader.readLine() } returns "bad!" andThen VehicleType.BICYCLE.toString()
            every { writer.write(any<String>()) } returns Unit
            every { writer.flush() } returns Unit
            val value = ReaderUtils.readType<VehicleType>(reader, writer, hint, { ReaderUtils.toEnumCaster<VehicleType>(it) }, {true})
            verify {
                writer.write(hint)
                writer.flush()
                writer.write("Некорректный ввод enum.\n")
                writer.flush()
            }
            confirmVerified(writer)
            assertEquals(VehicleType.BICYCLE, value)
        }

        @Test fun readEnumTwoErrors() {
            every { reader.readLine() } returns "bad!" andThen "bad again!" andThen VehicleType.BICYCLE.toString()
            every { writer.write(any<String>()) } returns Unit
            every { writer.flush() } returns Unit
            val value = ReaderUtils.readType<VehicleType>(reader, writer, hint, { ReaderUtils.toEnumCaster<VehicleType>(it) }, {true})
            verify {
                writer.write(hint)
                writer.flush()
                writer.write("Некорректный ввод enum.\n")
                writer.flush()
                writer.write("Некорректный ввод enum.\n")
                writer.flush()
            }
            confirmVerified(writer)
            assertEquals(VehicleType.BICYCLE, value)
        }
    }

    @Nested inner class ReadVehicle {
        val hint = "sample hint"
        val reader = mockk<BufferedReader>()
        val writer = mockk<BufferedWriter>(relaxed=true)

        @AfterEach fun cleanUp() {
            clearAllMocks()
        }

        @Test fun read() {
            val badInput = "Некорректный ввод. Попробуйте еще.\n"
            val vehicle = VehicleFactory.generateRandomVehicle().copy(id=1)
            every { reader.readLine() } returnsMany listOf(
                "", // empty name
                vehicle.name,
                "-60000",
                vehicle.coordinates.x.toString(),
                vehicle.coordinates.y.toString(),
                "-12121.21212",
                vehicle.enginePower.toString(),
                vehicle.type.toString(),
                vehicle.fuelType.toString(),
            )
            val actual = ReaderUtils.readVehicle(writer, reader)
            verify {
                writer.write("Введите название транспорта.\n")
                writer.flush()
                writer.write(badInput)
                writer.flush()
                writer.write("Введите переменную X > -523\n")
                writer.flush()
                writer.write(badInput)
                writer.flush()
                writer.write("Введите переменную Y\n")
                writer.flush()
                writer.write("Введите мощность двигателя > 0\n")
                writer.flush()
                writer.write(badInput)
                writer.flush()
                writer.write("Введите тип транспорта. Возможные значения: ${
                    VehicleType.values().map { it.toString() }
                } или оставьте строку пустой для null\n")
                writer.flush()
                writer.write("Введите тип топлива. Возможные значения: ${FuelType.values().map { it.toString() }}\n")
                writer.flush()
            }
            confirmVerified(writer)
            assertEquals(vehicle, actual)
        }
    }
}