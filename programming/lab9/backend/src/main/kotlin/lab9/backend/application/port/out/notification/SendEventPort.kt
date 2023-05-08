package lab9.backend.application.port.out.notification

import lab9.backend.domain.Event

@FunctionalInterface
interface SendEventPort {
    fun send(event: Event)
}