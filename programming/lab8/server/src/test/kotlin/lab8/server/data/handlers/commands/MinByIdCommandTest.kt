package lab8.server.data.handlers.commands

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import lab8.entities.dtos.requests.AddRequestDTO
import lab8.entities.dtos.requests.GetMinByIDRequestDTO
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle

class MinByIdCommandTest : DefaultCommandTest() {
    private val cmd = MinByIdCommand(useCase)
    init {
        "good request" should {
            val request = GetMinByIDRequestDTO(User.generateRandomUser())

            "be handled" {

                val veh = Vehicle.generateRandomVehicle()
                every {
                    useCase.getMinById()
                } returns veh
                cmd.execute(request).min shouldBe veh

                verify {
                    useCase.getMinById()
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
            val request = GetMinByIDRequestDTO(User.generateRandomUser())
            "be thrown up" {
                val error = Exception("test")
                every {
                    useCase.getMinById()
                } throws error
                val actual = shouldThrow<Exception> { cmd.execute(request) }
                actual shouldBe error

                verify {
                    useCase.getMinById()
                }
            }
        }
    }
}
