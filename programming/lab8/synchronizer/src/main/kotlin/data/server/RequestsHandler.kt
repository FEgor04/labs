package data.server

import domain.UseCase
import lab8.entities.synchronizer.SynchronizerRequest
import lab8.entities.synchronizer.SynchronizerResponse
import lab8.logger.KCoolLogger
import lab8.udp.server.RequestsHandler
import java.net.SocketAddress

class SynchronizerRequestsHandler(val usecase: UseCase): RequestsHandler<SynchronizerRequest, SynchronizerResponse> {
    private val logger by KCoolLogger()
    override suspend fun handle(
        request: SynchronizerRequest,
        addr: SocketAddress
    ): Pair<SynchronizerResponse, SocketAddress> {
        logger.info("Request from $addr")
        when(request) {
            is SynchronizerRequest.Notify -> {
                usecase.notify(request.event)
            }
            is SynchronizerRequest.Subscribe -> {
                usecase.addSubscriber(addr)
            }
            is SynchronizerRequest.Unsubscribe -> {
                usecase.unsubscribe(addr)
            }
        }
        return Pair(SynchronizerResponse.Success(), addr)
    }
}