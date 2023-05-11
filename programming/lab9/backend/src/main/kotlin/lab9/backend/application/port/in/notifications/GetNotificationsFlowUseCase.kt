package lab9.backend.application.port.`in`.notifications

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import lab9.backend.common.UseCase
import lab9.backend.domain.Notification

@UseCase
interface GetNotificationsFlowUseCase {
    fun getNotificationsFlow(): Flow<Notification>
}