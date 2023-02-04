package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test

class RemoveKeyCommandTest : CommandTest() {
    @Test
    fun ok() {
        val id = 123;
        val cmd = RemoveKeyCommand(repository)
        every { repository.removeVehicle(id) } returns Unit

        cmd.handle("remove_key $id", writer, reader)

        verify {
            repository.removeVehicle(id)
            writer.write("Успех!\n")
            writer.flush()
        }
        confirmVerified(writer)
        confirmVerified(repository)
    }

    @Test
    fun `bad input`() {
        val id = "bad_input";
        val cmd = RemoveKeyCommand(repository)
        cmd.handle("remove_key $id", writer, reader)
        verify {
            writer.write("Неправильный ввод.\n")
            writer.flush()
        }
        confirmVerified(writer)
    }
}