package lab5.cli.commands

import io.mockk.clearAllMocks
import io.mockk.mockk
import lab5.repositories.VehicleRepository
import org.junit.jupiter.api.AfterEach
import java.io.BufferedReader
import java.io.BufferedWriter

open class CommandTest {
    protected val reader = mockk<BufferedReader>()
    protected val writer = mockk<BufferedWriter>(relaxed = true)
    protected val repository = mockk<VehicleRepository>()

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }
}