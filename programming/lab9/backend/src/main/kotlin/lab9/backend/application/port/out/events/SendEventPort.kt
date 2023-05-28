package lab9.backend.application.port.out.events

import lab9.backend.domain.Event
import org.springframework.stereotype.Component

@FunctionalInterface
@Component
interface SendEventPort {
    fun send(event: Event)
}