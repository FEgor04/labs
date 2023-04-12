package lab8.server.data.handlers

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import lab8.entities.dtos.requests.SignUpRequest
import lab8.entities.dtos.responses.SignUpResponse
import lab8.entities.user.User
import lab8.exceptions.ServerException
import lab8.server.domain.usecases.CommandsHandlerUseCase
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ExecutorTest {
    val usecase = mockk<CommandsHandlerUseCase>()
    val executor = Executor(usecase)

    @Nested
    inner class `Sign Up Command` {
        @Test
        fun `user does not exists`(): Unit = runBlocking {
            val expected = User.generateRandomUser()
            val request = SignUpRequest(
                expected.copy(id = -1)
            )
            coEvery { usecase.createUser(expected.name, expected.password) } returns expected.id
            val resposne = executor.handle(request)
            require(resposne is SignUpResponse)
            resposne.user shouldBe expected
        }

        @Test
        fun `user already exists`(): Unit = runBlocking {
            val user = User.generateRandomUser()
            val request = SignUpRequest(
                user.copy(id = -1)
            )
            coEvery { usecase.createUser(user.name, user.password) } throws ServerException.UserAlreadyExistsException(
                user.name
            )
            assertThrows<ServerException.UserAlreadyExistsException> {
                executor.handle(request)
            }
        }
    }
}