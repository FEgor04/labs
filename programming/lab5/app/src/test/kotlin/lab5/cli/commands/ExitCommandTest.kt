package lab5.cli.commands

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ExitCommandTest : CommandTest() {
    @Test fun ok() {
        var shouldBeOkAfterExecuting = false
        var cmd = ExitCommand({shouldBeOkAfterExecuting = true})
        cmd.handle("exit", writer, reader)
        assert(shouldBeOkAfterExecuting)
    }
}