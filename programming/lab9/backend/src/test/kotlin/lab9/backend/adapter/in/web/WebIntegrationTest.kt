package lab9.backend.adapter.`in`.web

import lab9.backend.BackendApplication
import org.junit.ClassRule
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [BackendApplication::class]
)
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
@ContextConfiguration(initializers = [WebIntegrationTest.Initializer::class])
open class WebIntegrationTest(
    @Autowired
    protected val mockMvc: MockMvc,
) {
    companion object {
        @JvmField
        @ClassRule
        val postgresContainer = PostgreSQLContainer("postgres:13.3")
            .withDatabaseName("integration-tests")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432)!!

        init {
            postgresContainer.start()
        }

    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=${postgresContainer.jdbcUrl}",
                "spring.datasource.username=${postgresContainer.username}",
                "spring.datasource.password=${postgresContainer.password}",
                "spring.datasource-driver-class=${postgresContainer.driverClassName}"
            ).applyTo(applicationContext.environment);
        }
    }
}