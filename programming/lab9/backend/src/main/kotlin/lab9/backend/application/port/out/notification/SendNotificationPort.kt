package lab9.backend.application.port.out.notification

import lab9.backend.domain.Notification

interface SendNotificationPort {
    fun notify(notification: Notification)
}