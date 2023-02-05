package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class CountLessThanEnginePowerCommandTest : CommandTest() {
    @ParameterizedTest
    @MethodSource("power")
    fun execute(power: Double) {
        val cmd = CountLessThanEnginePowerCommand(repository)
        every { repository.countLessThanEnginePower(power) } returns 10
        cmd.handle("count_less_than_engine_power $power", writer, reader)
        verify {
            writer.write("В коллекции содержиться 10 элементов с мощностью двигателя меньше чем $power\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test
    fun `empty input`() {
        val cmd = CountLessThanEnginePowerCommand(repository)
        cmd.handle("count_less_than_engine_power     ", writer, reader)
        verify {
            writer.write("Некорректный ввод.\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test
    fun `bad input`() {
        val cmd = CountLessThanEnginePowerCommand(repository)
        cmd.handle("count_less_than_engine_power  nan", writer, reader)
        verify {
            writer.write("Некорректный ввод.\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @ParameterizedTest
    @MethodSource("power")
    fun `test check`(power: Double) {
        val cmd = CountLessThanEnginePowerCommand(repository)
        assert(cmd.check("count_less_than_engine_power $power   "))
        assert(cmd.check("count_less_than_engine_power       $power    "))
        assert(cmd.check("     count_less_than_engine_power       $power   "))
        assert(cmd.check("     count_less_than_engine_power   $power     "))
        assert(!cmd.check("     count_less_than_engine_power"))
        assert(!cmd.check("     count_less_than_engine_power     "))
        assert(!cmd.check("     count_less_than_engine_power asdasdsa$power"))
        assert(!cmd.check("     count_less_than_engine_power adasds${power}dasdas"))
        assert(!cmd.check("     count_less_than_engine_power ${power}dasdas"))
    }

    companion object {
        @JvmStatic
        fun power() = (-1000..1000 step 100).map { it.toDouble() / 10 }
    }
}