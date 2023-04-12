package lab8.entities.user

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable data class User(val name: String, val password: String, val id: Int) {
    companion object {
        fun generateRandomUser(): User {
            val random = Random(java.time.LocalTime.now().toSecondOfDay())
            return User(
                name = "Test ${random.nextInt()}",
                password = "${random.nextInt()}",
                id = random.nextInt(1, Int.MAX_VALUE),
            )
        }
    }
}
