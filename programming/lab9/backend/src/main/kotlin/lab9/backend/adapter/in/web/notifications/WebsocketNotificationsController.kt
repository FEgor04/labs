package lab9.backend.adapter.`in`.web.notifications

import jakarta.annotation.PostConstruct
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab9.backend.application.port.`in`.notifications.GetNotificationsFlowUseCase
import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.domain.Notification
import lab9.backend.logger.KCoolLogger
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Controller
import java.util.concurrent.Executors

@Controller
class WebsocketNotificationsController(
        private val getNotificationsFlowUseCase: GetNotificationsFlowUseCase,
        private val getUserUseCase: GetUserUseCase,
        private val simpMessagingTemplate: SimpMessagingTemplate,
) {
    private val logger by KCoolLogger()

    private val listenThread = Executors.newSingleThreadExecutor { r ->
        Thread(r, "sse-notification-listener")
    }

    @PostConstruct
    fun startNotifying() {
        listenThread.execute {
            runBlocking {
                getNotificationsFlowUseCase.getNotificationsFlow().collect {
                    logger.info("Got new notification: $it")
                    handleNotification(it)
                }
            }
        }
    }

    fun handleNotification(notification: Notification) {
        val json = Json.encodeToString(notification)
        simpMessagingTemplate.convertAndSend("/topic/notifications", json)
    }

}