package lab7.server.presentation.cli.commands

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import lab7.entities.user.User
import lab7.entities.vehicle.Vehicle
import lab7.server.domain.auth.AuthManager
import lab7.server.domain.persistence.PersistenceManager
import java.sql.SQLException
import kotlin.random.Random

class GenerateRandomCommand(pManager: PersistenceManager, aManager: AuthManager) : CommandImpl(
    "generate_random",
    "сгенерировать n рандомных машин",
    "(\\d+)",
    fun(input, writer, _, _, checker) {
        val (nStr) = checker.find(input)!!.destructured
        val n = nStr.toInt()
        val random = Random(java.time.LocalTime.now().toNanoOfDay())
        val username = "${random.nextInt()}"
        val userPassword = "${random.nextInt()}"
        val id = runBlocking { aManager.createUser(username, userPassword) }
        val user = User(username, userPassword, id)
        val jobs = mutableListOf<Deferred<Int>>()
        try {
            runBlocking {
                repeat(n) {
                    jobs += async {
                        pManager.saveVehicle(
                            Vehicle.generateRandomVehicle().copy(authorID = user.id),
                            user.name
                        )
                    }
                }
                jobs.awaitAll()
            }
        } catch (e: SQLException) {
            writer.write("Ошибка: $e\n")
        }
        writer.flush()
    }

)
