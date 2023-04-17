package lab9.backend.users

import lab9.backend.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Int> {
    fun findByUsername(username: String): User?
}