package lab7.server.data.handlers.commands

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import lab7.entities.dtos.requests.AddRequestDTO
import lab7.entities.dtos.requests.CountByTypeRequestDTO
import lab7.entities.user.User
import lab7.entities.vehicle.Vehicle
import lab7.entities.vehicle.VehicleType

class CountByTypeCommandTest : DefaultCommandTest() {
    private val cmd = CountByTypeCommand(useCase)
    init {
        "good request" should {
            val type = VehicleType.BOAT
            val request = CountByTypeRequestDTO(type, User.generateRandomUser())

            "be handled" {

                val count = 10
                every {
                    useCase.countByType(any())
                } returns count
                cmd.execute(request).count shouldBe count

                verify {
                    useCase.countByType(type)
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
            val type = VehicleType.BOAT
            val request = CountByTypeRequestDTO(type, User.generateRandomUser())

            "be thrown up" {
                val error = Exception("test")
                every {
                    useCase.countByType(any())
                } throws error
                val actual = shouldThrow<Exception> { cmd.execute(request).count }
                actual shouldBe error

                verify {
                    useCase.countByType(type)
                }
            }
        }
    }
}
