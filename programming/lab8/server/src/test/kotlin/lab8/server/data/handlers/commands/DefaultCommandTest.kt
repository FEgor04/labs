package lab8.server.data.handlers.commands

import io.kotest.core.spec.style.WordSpec
import io.mockk.mockk
import lab8.server.domain.usecases.CommandsHandlerUseCase

abstract class DefaultCommandTest : WordSpec() {
    protected val useCase: CommandsHandlerUseCase = mockk<CommandsHandlerUseCase>()
}
