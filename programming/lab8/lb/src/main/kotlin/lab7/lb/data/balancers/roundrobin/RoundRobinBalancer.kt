package lab7.lb.data.balancers.roundrobin

import lab7.entities.balancer.NodeAddress
import lab7.lb.domain.balancer.LoadBalancer
import lab7.logger.KCoolLogger
import java.util.concurrent.atomic.AtomicInteger

class RoundRobinBalancer(private val nodes: MutableSet<NodeAddress>) : LoadBalancer {
    private var lastNumber: AtomicInteger = AtomicInteger(0)
    private val logger by KCoolLogger()

    override fun getNode(): NodeAddress {
        logger.info("Getting another node")
        return nodes[lastNumber.getAndIncrement() % nodes.size]
    }

    override fun addNode(addr: NodeAddress): Int {
        nodes.add(addr)
        logger.info("Added new node $addr. Total nodes: ${nodes.size}")
        return nodes.size
    }
}

@Suppress("TooGenericExceptionThrown")
private operator fun <E> MutableSet<E>.get(i: Int): E {
    var num = 0
    forEach {
        if (num == i) {
            return it
        }
        num++
    }
    throw Exception("WTF")
}
