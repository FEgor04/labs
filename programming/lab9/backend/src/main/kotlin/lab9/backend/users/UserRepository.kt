package lab9.backend.users

import lab9.backend.entities.User
import lab9.backend.entities.Vehicle
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: PagingAndSortingRepository<User, Int>, JpaRepository<User, Int> {
    fun findByUsername(username: String): User?
}