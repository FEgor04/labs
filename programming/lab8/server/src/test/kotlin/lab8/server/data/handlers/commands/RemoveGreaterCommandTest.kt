package lab8.server.data.handlers.commands

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import lab8.entities.dtos.requests.AddRequestDTO
import lab8.entities.dtos.requests.RemoveGreaterRequestDTO
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle

class RemoveGreaterCommandTest : DefaultCommandTest() {
    private val cmd = RemoveGreaterCommand(useCase)

    init {
        "good request" should {
            val request = RemoveGreaterRequestDTO(Vehicle.generateRandomVehicle(), User.generateRandomUser())

            "be handled" {

                val cnt = 10
                coEvery {
                    useCase.removeGreater(request.vehicle, request.user)
                } returns cnt
                cmd.execute(request).cnt shouldBe cnt

                coVerify {
                    useCase.removeGreater(request.vehicle, request.user)
                }
            }

            "be checked" {
                cmd.check(request) shouldBe true
            }
        }

        "bad request" should {
            val request = AddRequestDTO(Vehicle.generateRandomVehicle(), User.generateRandomUser())
            "not be checked" {
                cmd.check(request) shouldBe false
            }
        }

        "database error" should {
            val request = RemoveGreaterRequestDTO(Vehicle.generateRandomVehicle(), User.generateRandomUser())
            "be thrown up" {
                val error = Exception("test")
                coEvery { useCase.removeGreater(any(), any()) } throws error
                val actual = shouldThrow<Exception> { cmd.execute(request) }
                actual shouldBe error

                coVerify { useCase.removeGreater(request.vehicle, request.user) }
            }
        }
    }
}
