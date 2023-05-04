package lab9.backend.adapter.out.persistence.user

import lab9.backend.application.port.`in`.signup.UserAlreadyExistsException
import lab9.backend.application.port.out.user.CreateUserPort
import lab9.backend.application.port.out.user.LoadUserPort
import lab9.backend.common.PersistenceAdapter
import lab9.backend.domain.User
import lab9.backend.logger.KCoolLogger
import org.springframework.dao.DataIntegrityViolationException

@PersistenceAdapter
class UserPersistenceAdapter(
    private val userRepository: UserRepository
) : LoadUserPort, CreateUserPort {
    val logger by KCoolLogger()

    override fun loadUserByUsername(username: String): User? {
        val jpaUser = userRepository.findByUsername(username) ?: return null
        return jpaUser.toDomain()
    }

    override fun loadUserById(id: User.UserID): User? {
        val jpaUser = userRepository.findById(id.id) ?: return null
        if(jpaUser.isEmpty) {
            return null
        }
        return jpaUser.get().toDomain()
    }

    override fun createUser(user: User): User {
        logger.info("Saving new user ${user}")
        return try {
            val jpaUser = userRepository.saveAndFlush(UserJpaEntity(null, user.username, user.password, emptySet()))
            logger.info("Successfully saved new user ${user}")
            jpaUser.toDomain()
        } catch(e: DataIntegrityViolationException) {
            logger.info("User with such name already exists!")
            throw UserAlreadyExistsException()
        }
    }


}