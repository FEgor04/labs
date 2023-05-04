package lab9.backend.application.port.out.notification

import kotlinx.coroutines.flow.SharedFlow
import lab9.backend.domain.Notification

interface SubscribeToNotificationsPort {
    fun subscribe(): SharedFlow<Notification>
}