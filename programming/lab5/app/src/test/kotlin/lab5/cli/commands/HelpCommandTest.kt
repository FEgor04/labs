package lab5.cli.commands

import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.util.*

class HelpCommandTest : CommandTest() {

    @Test
    fun `no commands`() {
        val command = HelpCommand(listOf())
        command.handle("help", reader = reader, writer = writer)
    }

    @Test
    fun `one command`() {
        val command = HelpCommand(listOf(object : Command {
            override fun check(userInput: String): Boolean {
                TODO("Not yet implemented")
            }

            override fun handle(
                userInput: String,
                writer: BufferedWriter,
                reader: BufferedReader,
                executeCommandStackTrace: Stack<File>
            ) {
                TODO("Not yet implemented")
            }

            override fun toString(): String {
                return "Command!"
            }
        }))
        command.handle("help", reader = reader, writer = writer)
        verify {
            writer.write("- Command!\n")
            writer.flush()
        }
        confirmVerified(writer)
    }
}