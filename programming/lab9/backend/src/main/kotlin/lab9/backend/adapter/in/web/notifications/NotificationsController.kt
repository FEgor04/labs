package lab9.backend.adapter.`in`.web.notifications

import jakarta.servlet.http.HttpServletRequest
import lab9.backend.domain.Event
import org.springframework.http.MediaType
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.CopyOnWriteArraySet

@RestController
class NotificationsController {
    private val clients: MutableSet<SseEmitter> = CopyOnWriteArraySet()

    @RequestMapping("/notifications-stream", method = [RequestMethod.GET])
    fun events(request: HttpServletRequest): SseEmitter {
        val emitter = SseEmitter()
        clients.add(emitter)
        emitter.onTimeout {
            clients.remove(emitter)
        }
        emitter.onCompletion {
            clients.remove(emitter)
        }
        return emitter
    }

    @Async
    @KafkaListener()
    fun handleEvent(event: Event) {
        val deadEmitters: MutableList<SseEmitter> = ArrayList()
        clients.forEach { emitter ->
            try {
                emitter.send(event, MediaType.APPLICATION_JSON)
            } catch (ignore: Exception) {
                deadEmitters.add(emitter)
            }
        }
        clients.removeAll(deadEmitters.toSet())
    }
}