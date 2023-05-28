package lab9.backend.application.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import lab9.backend.application.port.`in`.notifications.GetNotificationsFlowUseCase
import lab9.backend.application.port.out.events.SubscribeToEventsPort
import lab9.backend.domain.Event
import lab9.backend.domain.Notification
import org.springframework.stereotype.Service

@Service
class GetNotificationsFlowService(
        private val subscribeToEventsPort: SubscribeToEventsPort
) : GetNotificationsFlowUseCase {
    val eventsFlow = subscribeToEventsPort.subscribe().filter {
        it is Event.AccessChanged || it is Event.NewVehicle || it is Event.VehicleDeleted || it is Event.VehicleUpdated
    }
            .map { eventToNotification(it) }

    override fun getNotificationsFlow(): Flow<Notification> {
        return eventsFlow
    }

    private fun eventToNotification(event: Event): Notification {
        return when (event) {
            is Event.AccessChanged -> Notification.AccessChanged(
                    fromUser = event.byWhom.id,
                    toUser = event.toWho.id,
                    canEdit = event.canEdit,
                    canDelete = event.canDelete
            )

            is Event.NewVehicle -> Notification.VehicleCreated(
                    vehicleId = event.vehicleId.id,
                    authorId = event.authorID.id
            )

            is Event.VehicleDeleted -> Notification.VehicleDeleted(
                    vehicleId = event.vehicleId.id,
                    deletedBy = event.deletedBy.id
            )

            is Event.VehicleUpdated -> Notification.VehicleUpdated(
                    vehicleId = event.vehicleID.id,
                    updatedBy = event.actorId.id
            )
        }
    }
}