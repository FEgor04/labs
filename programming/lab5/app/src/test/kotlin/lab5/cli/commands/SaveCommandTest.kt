package lab5.cli.commands

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test

class SaveCommandTest : CommandTest() {
    @Test fun ok() {
        val cmd = SaveCommand(repository)
        every { repository.saveCollection() } returns Unit
        cmd.handle("save", writer, reader)
        verify {
            repository.saveCollection()
            writer.write("Успех!")
            writer.flush()
        }
        confirmVerified(repository)
        confirmVerified(writer)
    }

    @Test fun `exception on save`() {
        val cmd = SaveCommand(repository)
        val e: Exception = Exception("not today")
        every { repository.saveCollection() } throws e
        cmd.handle("save", writer, reader)
        verify {
            repository.saveCollection()
            writer.write("Не удалось сохранить коллекцию. Ошибка: $e\n")
            writer.flush()
        }
        confirmVerified(repository)
        confirmVerified(writer)
    }
}