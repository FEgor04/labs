package lab9.backend.users

import lab9.backend.entities.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder) : UserService {
    override fun createUser(name: String, password: String): User {
        return userRepository.save(
            User(
                id = -1,
                username = name,
                password = passwordEncoder.encode(password),
                vehicles = emptySet()
            )
        )
    }

    override fun authenticateUser(name: String, password: String): User {
        val user = userRepository.findByUsername(name)
            ?: throw UsernameNotFoundException("Wrong password or no such user exists")
        if (passwordEncoder.matches(password, user.password)) {
            return user
        }
        throw UsernameNotFoundException("Wrong password or no such user exists")
    }

    override fun getUserByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

}