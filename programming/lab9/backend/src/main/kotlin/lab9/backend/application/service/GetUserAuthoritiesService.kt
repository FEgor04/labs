package lab9.backend.application.service

import lab9.backend.application.port.`in`.authorities.GetUserAuthoritiesUseCase
import lab9.backend.application.port.out.authorities.GetUserAuthoritiesToDeletePort
import lab9.backend.application.port.out.authorities.GetUserAuthoritiesToEditPort
import lab9.backend.application.port.out.authorities.GetUserGivenAuthoritiesPort
import lab9.backend.domain.User
import org.springframework.stereotype.Service

@Service
class GetUserAuthoritiesService(
        private val getUserAuthoritiesToDeletePort: GetUserAuthoritiesToDeletePort,
        private val getUserAuthoritiesToEditPort: GetUserAuthoritiesToEditPort,
        private val getUserGivenAuthoritiesPort: GetUserGivenAuthoritiesPort,
) : GetUserAuthoritiesUseCase {
    override fun getUserAuthoritiesToDelete(userId: User.UserID): List<User.UserID> {
        return getUserAuthoritiesToDeletePort.getUserAuthoritiesToDelete(userId)
    }

    override fun getUserAuthoritiesToEdit(userId: User.UserID): List<User.UserID> {
        return getUserAuthoritiesToEditPort.getUserAuthoritiesToEdit(userId)
    }

    override fun getUserGivenAuthoritiesToEdit(userId: User.UserID): List<User.UserID> {
        return getUserGivenAuthoritiesPort.getUserGivenAuthoritiesToEdit(userId)
    }

    override fun getUserGivenAuthoritiesToDelete(userId: User.UserID): List<User.UserID> {
        return getUserGivenAuthoritiesPort.getUserGivenAuthoritiesToDelete(userId)
    }


}