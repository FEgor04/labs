package lab5.cli.commands

import io.mockk.*
import lab5.cli.CLIHandler
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.lang.Exception
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExecuteScriptCommandTest : CommandTest() {
    @Test fun `recursion`() {
        val fileReader = mockk<InputStreamReader>()

        val handler = mockk<CLIHandler>();
        val  stackTrace = Stack<File>()
        stackTrace.push(File("test1"))
        stackTrace.push(File("test2"))
        val cmd = ExecuteScriptCommand(handler, { it -> fileReader })
        cmd.handle("execute_script test1", writer, reader, stackTrace)
        verify {
            writer.write("https://youtu.be/dQw4w9WgXcQ\n")
            writer.flush()
        }
    }

    @Test fun `no file`() {
        val handler = mockk<CLIHandler>();
        val err = FileNotFoundException("not today")
        val cmd = ExecuteScriptCommand(handler, { it -> throw err })
        cmd.handle("execute_script test1", writer, reader)
        verify {
            writer.write("Не существует файла test1: $err\n")
            writer.flush()
        }
    }

    @Test fun `security exception`() {
        val handler = mockk<CLIHandler>();
        val err = SecurityException("not today")
        val cmd = ExecuteScriptCommand(handler, { it -> throw err })
        cmd.handle("execute_script test1", writer, reader)
        verify {
            writer.write("Недостаточно прав для доступа к файлу $err\n")
            writer.flush()
        }
    }

    @Test fun `some other exception exception`() {
        val handler = mockk<CLIHandler>();
        val err = Exception("not today")
        val cmd = ExecuteScriptCommand(handler) { it -> throw err }
        cmd.handle("execute_script test1", writer, reader)
        verify {
            writer.write("Ошибка открытия файла: $err\n")
            writer.flush()
        }
    }
}