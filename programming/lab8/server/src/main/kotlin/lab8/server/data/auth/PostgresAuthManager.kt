package lab8.server.data.auth

import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.*
import lab8.logger.KCoolLogger
import lab8.server.domain.auth.AuthManager
import lab8.server.utilities.postgres.prepareStatementSuspend
import lab8.server.utilities.postgres.setArgs
import java.security.MessageDigest
import java.sql.Connection
import java.sql.SQLException
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Suppress("MagicNumber")
class PostgresAuthManager(private val pool: HikariDataSource) : AuthManager {
    val logger by KCoolLogger()

    @OptIn(DelicateCoroutinesApi::class)
    val authManagerDispatcher = Dispatchers.IO

    companion object {
        private const val pepper = "ПАНКИ_ХОЙ"
        private const val englishAlphabetLength = 26
        private const val saltLength = 10
    }

    private suspend fun getConnection(): Connection {
        var delay: Duration = 100.toDuration(DurationUnit.MILLISECONDS)
        while (true) {
            try {
                return withContext(authManagerDispatcher) { pool.connection }
            } catch (e: SQLException) {
                logger.error("Could not get connection: $e! Retrying!")
            }
            delay(delay)
            delay = delay.times(2)
        }
    }

    override suspend fun createUser(login: String, password: String): Int {
        logger.info("Creating new user with login = $login")
        val salt = generateSalt()
        val connection = getConnection()
        connection.use {
            val createUserStatement = connection.prepareStatementSuspend(
                "INSERT INTO users(name, password, salt) VALUES (?, ?, ?) RETURNING id",
                authManagerDispatcher,
            )

            val passwordHash = generatePasswordHash(password, salt)
            createUserStatement.setArgs(login, passwordHash, salt)
            val res = withContext(authManagerDispatcher) { createUserStatement.executeQuery() }
            res.next()
            logger.info("Success!!")
            return res.getInt(1)
        }
    }

    /**
     * Генерирует соль о 10 символах
     */
    private fun generateSalt(): String {
        val random = Random(java.time.LocalTime.now().toNanoOfDay())
        return (1..saltLength).map {
            Char(random.nextInt(englishAlphabetLength) + 'a'.code)
        }.fold("") { str, new -> str + new }
    }

    private fun generatePasswordHash(password: String, salt: String): String {
        val sha256 = MessageDigest.getInstance("SHA-256")
        val passwordHash = sha256.digest((pepper + password + salt).toByteArray()).fold("") { str, new ->
            str + "%02x".format(
                new
            )
        }
        return passwordHash
    }

    @Suppress("ReturnCount")
    override suspend fun authUser(login: String, password: String): Int {
        logger.info("Checking user $login password.")
        val connection = getConnection()
        connection.use {
            val getUserSaltStatement =
                connection.prepareStatementSuspend(
                    "SELECT id, password, salt FROM users WHERE name = ?",
                    authManagerDispatcher,
                )
            getUserSaltStatement.setArgs(login)
            val result = withContext(authManagerDispatcher) { getUserSaltStatement.executeQuery() }
            if (!result.next()) {
                logger.warn("Bad password for user $login")
                return 0
            }
            val id = result.getInt("id")
            val salt = result.getString("salt")
            val expectedHashedPassword = result.getString("password")
            val actualHashedPassword = generatePasswordHash(password, salt)
            if (expectedHashedPassword == actualHashedPassword) {
                logger.info("Good password! ID is $id")
                return id
            }
            logger.warn(
                "Bad password for user $login. Expected: $expectedHashedPassword, got $actualHashedPassword instead"
            )
            return 0
        }
    }
}
