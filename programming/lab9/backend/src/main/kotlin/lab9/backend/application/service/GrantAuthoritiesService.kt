package lab9.backend.application.service

import lab9.backend.application.port.`in`.authorities.GrantAuthoritiesUseCase
import lab9.backend.application.port.out.authorities.GetUserAuthoritiesToDeletePort
import lab9.backend.application.port.out.authorities.GetUserAuthoritiesToEditPort
import lab9.backend.application.port.out.authorities.SetUserAuthoritiesPort
import lab9.backend.application.port.out.events.SendEventPort
import lab9.backend.domain.Event
import lab9.backend.domain.User
import org.springframework.stereotype.Service

@Service
class GrantAuthoritiesService(
        private val setUserAuthoritiesPort: SetUserAuthoritiesPort,
        private val sendEventPort: SendEventPort,
) : GrantAuthoritiesUseCase {
    override fun grantAuthorities(from: User.UserID, to: User.UserID, canEdit: Boolean, canDelete: Boolean) {
        setUserAuthoritiesPort.setUserAuthoritiesToDelete(
                userID = to,
                ownerId = from,
                canDelete = canDelete,
                canEdit = canEdit
        )
        sendEventPort.send(Event.AccessChanged(
                from, to, canEdit, canDelete
        ))
    }
}