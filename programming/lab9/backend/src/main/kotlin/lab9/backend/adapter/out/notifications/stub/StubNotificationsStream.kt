package lab9.backend.adapter.out.notifications.stub

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import lab9.backend.application.port.out.notification.SendNotificationPort
import lab9.backend.application.port.out.notification.SubscribeToNotificationsPort
import lab9.backend.domain.Notification
import lab9.backend.logger.KCoolLogger
import org.springframework.stereotype.Component

@Component
class StubNotificationsStream : SendNotificationPort, SubscribeToNotificationsPort {
    val logger by KCoolLogger()
    val flow: MutableSharedFlow<Notification> =
        MutableSharedFlow(onBufferOverflow = BufferOverflow.DROP_OLDEST) // tryEmit awlays returns true

    override fun notify(notification: Notification) {
        logger.info("New notification: $notification")
        flow.tryEmit(notification)
    }

    override fun subscribe(): SharedFlow<Notification> {
        logger.info("New subscriber")
        return this.flow.asSharedFlow()
    }
}