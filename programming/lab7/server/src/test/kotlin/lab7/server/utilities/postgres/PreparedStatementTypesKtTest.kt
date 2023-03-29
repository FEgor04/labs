package lab7.server.utilities.postgres

import io.kotest.core.spec.style.WordSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.sql.PreparedStatement

class PreparedStatementTypesKtTest : WordSpec({
    "int long double" should {
        "be handled good" {
            val statement = mockk<PreparedStatement>()
            val args = arrayOf<Any>("hello", 123, 12.3)
            every {
                statement.setString(1, "hello")
                statement.setInt(2, 123)
                statement.setDouble(3, 12.3)
            } returns Unit
            statement.setArgs(*args)
            verify {
                statement.setString(1, "hello")
                statement.setInt(2, 123)
                statement.setDouble(3, 12.3)
            }
        }
    }
})
