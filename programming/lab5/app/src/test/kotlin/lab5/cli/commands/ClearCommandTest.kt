package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test

class ClearCommandTest : CommandTest() {

    @Test
    fun test() {
        val cmd = ClearCommand(repository)
        every { repository.clear() } returns Unit
        cmd.handle("clear", reader = reader, writer = writer)
        verify {
            writer.write("Коллекция очищена\n")
            writer.flush()
        }
        confirmVerified(writer)
    }

    @Test
    fun check() {
        val cmd = ClearCommand(repository)
        assert(cmd.check("clear"))
        assert(cmd.check("clear       "))
        assert(cmd.check("    clear       "))
    }
}