package lab9.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class BackendApplication

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
