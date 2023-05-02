package lab9.backend.adapter.out.persistence

import lab9.backend.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: PagingAndSortingRepository<UserJpaEntity, Int>, JpaRepository<UserJpaEntity, Int> {
    fun findByUsername(username: String): UserJpaEntity?
    override fun findById(id: Int): Optional<UserJpaEntity>
}