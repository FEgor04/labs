package lab8.client.presentation.cli.commands

import kotlinx.coroutines.runBlocking
import lab8.client.domain.RemoteCommandHandler
import lab8.entities.vehicle.Vehicle
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 * Класс команды remove_key
 */
@OptIn(ExperimentalTime::class)
class GenerateRandomCommand(handler: RemoteCommandHandler) : CommandImpl(
    "generate_random",
    "генерирует n рандомных транспортных средств и добавляет в коллекцию",
    "(\\d+)",
    fun(userInput, writer, _, _, regex) {
        val n: Int
        measureTime {
            val (idStr) = regex.find(userInput)?.destructured!!
            n = idStr.toInt()
            repeat(n) {
                runBlocking {
//                    var jobs = mutableListOf<Deferred<Unit>>();
//                    jobs.add(async(Dispatchers.IO) {
                    handler.add(Vehicle.generateRandomVehicle())
//                        Unit
//                    })
//                    jobs.awaitAll()
                }
            }
            writer.write("Успех!\n")
            writer.flush()
        }.apply {
            println("CREATED ALL VEHICLES. IT TOOK $this SECONDS. ${this.div(n)} on each vehicle")
        }
    },
) {
    override fun toString(): String {
        return "${this.commandName} n - ${this.description}"
    }
}
