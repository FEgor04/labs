package lab8.server.data.handlers.commands

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import lab8.entities.dtos.requests.AddRequestDTO
import lab8.entities.dtos.requests.UpdateVehicleByIdRequestDTO
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle

class UpdateCommandTest : DefaultCommandTest() {
    private val cmd = UpdateCommand(useCase)

    init {
        "good request" should {
            val user = User.generateRandomUser()
            val veh = Vehicle.generateRandomVehicle().copy(authorID = user.id)
            val request = UpdateVehicleByIdRequestDTO(veh.id, veh, user)

            "be handled" {

                coEvery { useCase.update(veh.id, veh, user) } returns Unit
                cmd.execute(request)

                coVerify { useCase.update(veh.id, veh, user) }
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
            val user = User.generateRandomUser()
            val veh = Vehicle.generateRandomVehicle().copy(authorID = user.id)
            val request = UpdateVehicleByIdRequestDTO(veh.id, veh, user)
            "be thrown up" {
                val error = Exception("test")
                coEvery { useCase.update(any(), any(), any()) } throws error
                val actual = shouldThrow<Exception> { cmd.execute(request) }
                actual shouldBe error

                coVerify { useCase.update(veh.id, veh, user) }
            }
        }
    }
}
