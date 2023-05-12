package lab9.backend.adapter.`in`.web.notifications

import jakarta.annotation.PostConstruct
import kotlinx.coroutines.flow.*
import kotlinx.serialization.encodeToString
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import lab9.backend.application.port.`in`.notifications.GetNotificationsFlowUseCase
import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.domain.Notification
import lab9.backend.domain.User
import lab9.backend.logger.KCoolLogger
import org.apache.kafka.common.utils.CopyOnWriteMap
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors

@RestController
@RequestMapping("/api")
class NotificationsController(
        private val getNotificationsFlowUseCase: GetNotificationsFlowUseCase,
        private val getUserUseCase: GetUserUseCase,
) {
    private val logger by KCoolLogger()
    private val listenThread = Executors.newSingleThreadExecutor {
        r -> Thread(r, "sse-notification-listener")
    }
    private val flow = getNotificationsFlowUseCase.getNotificationsFlow()
    val clients: MutableMap<UUID, MutableSharedFlow<String>> = CopyOnWriteMap()

    @GetMapping("/notifications-stream")
    fun streamNotifications(principal: Principal?): Flow<String> {
        val uuid = UUID.randomUUID()
        val user: User? = if (principal != null) {
            getUserUseCase.getUserByUsername(principal.name)
        } else {
            null
        }
        logger.info("New connection on notifications stream. User: ${user?.id}@$uuid")
        val flow = MutableSharedFlow<String>()
        clients[uuid] = flow
        flow.onCompletion {
            logger.info("Flow $uuid is complete")
        }
        flow.onSubscription {
            logger.info("Subscription on flow $uuid")
        }
        return flow.asSharedFlow()
    }

    @PostConstruct
    fun startListening() {
        listenThread.execute {
            runBlocking {
                getNotificationsFlowUseCase.getNotificationsFlow().collect { notification ->
                    val deadClients = CopyOnWriteArrayList<UUID>()
                    clients.forEach {
                        val client = it.key
                        val flow = it.value
                        try {
                            flow.emit(notificationJsonEncoder(notification))
                            logger.info("Emitted notification ${notification} to $client")
                        }
                        catch(e: Exception) {
                            logger.warn("Client $client is dead. Reason: $e")
                            deadClients.add(client)
                        }
                    }
                    deadClients.forEach {
                        clients.remove(it)
                    }
                }
            }
        }
    }

    private fun notificationJsonEncoder(notification: Notification): String = Json.encodeToString(notification)
}