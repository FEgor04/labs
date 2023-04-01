package lab8.server.data.handlers.commands

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import lab8.entities.CollectionInfo
import lab8.entities.dtos.requests.AddRequestDTO
import lab8.entities.dtos.requests.InfoRequestDTO
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle
import java.time.LocalDate

class InfoCommandTest : DefaultCommandTest() {
    private val cmd = InfoCommand(useCase)

    init {
        "good request" should {
            val request = InfoRequestDTO(User.generateRandomUser())

            "be handled" {

                val info = CollectionInfo(1, LocalDate.now(), "KEK")
                every {
                    useCase.collectionInfo
                } returns info
                cmd.execute(request).info shouldBe info

                verify {
                    useCase.collectionInfo
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
            val request = InfoRequestDTO(User.generateRandomUser())
            "be thrown up" {
                val error = Exception("test")
                every { useCase.collectionInfo } throws error
                val actual = shouldThrow<Exception> { cmd.execute(request) }
                actual shouldBe error

                verify { useCase.collectionInfo }
            }
        }
    }
}
