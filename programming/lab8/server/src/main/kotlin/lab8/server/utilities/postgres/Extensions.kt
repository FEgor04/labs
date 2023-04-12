package lab8.server.utilities.postgres

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.intellij.lang.annotations.Language
import java.sql.Connection
import java.sql.PreparedStatement

fun PreparedStatement.setArgs(vararg values: Any?) {
    values.forEachIndexed { id, arg ->
        when (arg) {
            is Int -> {
                this.setInt(id + 1, arg)
            }

            is Double -> {
                this.setDouble(id + 1, arg)
            }

            is Long -> {
                this.setLong(id + 1, arg)
            }

            else -> {
                if (arg == null) {
                    this.setNull(id + 1, 0)
                } else {
                    this.setString(id + 1, arg.toString())
                }
            }
        }
    }
}

suspend fun Connection.prepareStatementSuspend(
    @Language("SQL") statement: String,
    dispatcher: CoroutineDispatcher
): PreparedStatement =
    withContext(dispatcher) {
        prepareStatement(statement)
    }
